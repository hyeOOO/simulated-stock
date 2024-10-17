package toyproject.simulated_stock.domain.stock.order.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.auth.exception.AuthException;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;
import toyproject.simulated_stock.domain.stock.order.dto.StockOrderListDto;
import toyproject.simulated_stock.domain.stock.order.entitiy.*;
import toyproject.simulated_stock.domain.stock.order.repository.StockOrderRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserStockRepository;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.api.exception.ExceptionCode;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static toyproject.simulated_stock.api.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockOrderService {

    private final UserAccountRepository userAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final UserStockRepository userStockRepository;
    private final StockListRepository stockListRepository;
    private final MemberRepository memberRepository;

    //매수 로직
    @Transactional
    public void buyStock(String memberId, String stockCode, String stockName, int quantity, BigDecimal price, MarketType marketType) {
        //주식 정보
        StockList stock = stockListRepository.findBysrtnCd(stockCode).get(0);
        //MarketType marketType = convertToMarketType(stock.getMrktCtg());

        //멤버 정보
        Member member = getMember(memberId);

        //매수하려는 유저의 계좌
        UserAccount userAccount = userAccountRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //가격 계산
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

        //잔액 확인 및 차감
        if (userAccount.getBalance().compareTo(totalAmount) < 0) {
            throw new BusinessLogicException(ExceptionCode.NOT_ENOUGH_MONEY);
        }
        userAccount.withdraw(totalAmount);

        //기존 보유 주식 확인 및 없으면 생성
        UserStock userStock = userStockRepository.findByMemberIdAndStockCode(memberId, stockCode)
                .orElseGet(() -> UserStock.createUserStock(String.valueOf(member.getId()), stockCode, stockName, marketType, userAccount));
        // 주식 매수 처리 (비즈니스 메소드 사용)
        userStock.buy(quantity, price);

        //주문 기록 생성
        // StockOrder 객체 생성은 생성 메소드 사용
        StockOrder stockOrder = StockOrder.createOrder(memberId, stockCode, stockName, marketType, quantity, price, OrderType.BUY, userAccount);

        // 주문 저장
        stockOrderRepository.save(stockOrder);
        userStockRepository.save(userStock);  // 보유 주식 상태 업데이트
        userAccountRepository.save(userAccount);  // 잔액 변경된 유저 계좌 저장
    }

    //매도
    public void sellStock(String memberId, String stockCode, String stockName, int quantity, BigDecimal price) {
        //멤버 정보
        Member member = getMember(memberId);

        //사용자의 주식 계좌 정보 조회
        UserAccount userAccount = userAccountRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //보유 주식 정보 조회
        UserStock userStock = userStockRepository.findByMemberIdAndStockCode(memberId, stockCode)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HAVE_NO_STOCK));

        //매도하려는 수량이 보유한 수량보다 많은지 확인
        if (userStock.getQuantity() < quantity) {
            throw new BusinessLogicException(ExceptionCode.NOT_ENOUGH_STOCK);
        }

        // 매도 처리 (주식 수량 차감, 계좌 잔액 증가)
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));
        userAccount.deposit(totalAmount); // 계좌에 잔액 추가
        userStock.sellStock(quantity, price); // 주식 수량 차감 및 가격 갱신

        // 주식 수량이 0이 되면 해당 보유 주식 삭제
        if (userStock.getQuantity() == 0) {
            userStockRepository.delete(userStock);
        }

        // 주문 기록 생성
        StockOrder stockOrder = StockOrder.createOrder(memberId, stockCode, stockName, userStock.getMrtgType(), quantity, price, OrderType.SELL, userAccount);

        // 주문 및 유저 정보 저장
        stockOrderRepository.save(stockOrder);
        userAccountRepository.save(userAccount);
    }

    // 보유 주식 수량 조회 로직
    public int getHoldingStockQuantity(String memberId, String stockCode) {
        // Optional<UserStock>을 통해 객체가 존재하는지 확인하고, 수량을 추출
        return userStockRepository.findByMemberIdAndStockCode(memberId, stockCode)
                .map(UserStock::getQuantity)
                .orElse(0);  // 주식이 없을 경우 0을 반환
    }

    //특정 회원의 모든 주문 기록 가져오기
    public List<StockOrderListDto> getAllOrdersByMemberKey(String memberKey) {
        Member member = getMember(memberKey);
        List<StockOrder> orders = stockOrderRepository.findByMemberId(String.valueOf(member.getId()));
        return orders.stream()
                .map(StockOrderListDto::fromEntity)  // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 특정 회원의 매수 기록을 DTO로 변환하여 가져오기
    public List<StockOrderListDto> getBuyOrdersByMemberKey(String memberKey) {
        Member member = getMember(memberKey);
        List<StockOrder> buyOrders = stockOrderRepository.findByMemberIdAndOrderType(String.valueOf(member.getId()), OrderType.BUY);
        return buyOrders.stream()
                .map(StockOrderListDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 회원의 매도 기록을 DTO로 변환하여 가져오기
    public List<StockOrderListDto> getSellOrdersByMemberKey(String memberKey) {
        Member member = getMember(memberKey);
        List<StockOrder> sellOrders = stockOrderRepository.findByMemberIdAndOrderType(String.valueOf(member.getId()), OrderType.SELL);
        return sellOrders.stream()
                .map(StockOrderListDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 평균 매입 가격 계산 메소드
    private BigDecimal calculateNewAvgPrice(BigDecimal currentAvgPrice, int currentQuantity, BigDecimal newPrice, int newQuantity) {
        BigDecimal totalCurrentAmount = currentAvgPrice.multiply(BigDecimal.valueOf(currentQuantity));
        BigDecimal totalNewAmount = newPrice.multiply(BigDecimal.valueOf(newQuantity));
        BigDecimal totalQuantity = BigDecimal.valueOf(currentQuantity + newQuantity);
        return (totalCurrentAmount.add(totalNewAmount)).divide(totalQuantity, RoundingMode.HALF_UP);
    }

    //MarketType Enum 변환 메소드
    public MarketType convertToMarketType(String marketTypeStr) {
        try {
            return MarketType.valueOf(marketTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid market type: " + marketTypeStr);
        }
    }

    //멤버 정보 가져오기
    private Member getMember(String memberKey) {
        Member member = memberRepository.findByMemberKey(memberKey)
                .orElseThrow(() -> new AuthException(MEMBER_NOT_FOUND));
        return member;
    }
}