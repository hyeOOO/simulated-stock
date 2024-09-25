package toyproject.simulated_stock.domain.stock.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.stock.order.entitiy.OrderType;
import toyproject.simulated_stock.domain.stock.order.entitiy.StockOrder;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserStock;
import toyproject.simulated_stock.domain.stock.order.repository.StockOrderRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserStockRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockOrderService {

    private final UserAccountRepository userAccountRepository;
    private final StockOrderRepository stockOrderRepository;
    private final UserStockRepository userStockRepository;

    //매수 로직
    @Transactional
    public void buyStock(String userId, String stockCode, int quantity, BigDecimal price) {
        //매수하려는 유저의 계좌
        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        //가격 계산
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

        //잔액 확인 및 차감
        userAccount.withdraw(totalAmount);

        //기존 보유 주식 확인 및 없으면 생성
        UserStock userStock = userStockRepository.findByUserIdAndStockCode(userId, stockCode)
                .orElseGet(() -> UserStock.createUserStock(userId, stockCode, userAccount));
        // 주식 매수 처리 (비즈니스 메소드 사용)
        userStock.buy(quantity, price);

        //주문 기록 생성
        // StockOrder 객체 생성은 생성 메소드 사용
        StockOrder stockOrder = StockOrder.createOrder(userId, stockCode, quantity, price, OrderType.BUY, userAccount);

        // 주문 저장
        stockOrderRepository.save(stockOrder);
        userStockRepository.save(userStock);  // 보유 주식 상태 업데이트
        userAccountRepository.save(userAccount);  // 잔액 변경된 유저 계좌 저장
    }

    //매도
    public void sellStock(String userId, String stockCode, int quantity, BigDecimal price) {
        //사용자의 주식 계좌 정보 조회

    }

    // 평균 매입 가격 계산 메소드
    private BigDecimal calculateNewAvgPrice(BigDecimal currentAvgPrice, int currentQuantity, BigDecimal newPrice, int newQuantity) {
        BigDecimal totalCurrentAmount = currentAvgPrice.multiply(BigDecimal.valueOf(currentQuantity));
        BigDecimal totalNewAmount = newPrice.multiply(BigDecimal.valueOf(newQuantity));
        BigDecimal totalQuantity = BigDecimal.valueOf(currentQuantity + newQuantity);
        return (totalCurrentAmount.add(totalNewAmount)).divide(totalQuantity, RoundingMode.HALF_UP);
    }
}
