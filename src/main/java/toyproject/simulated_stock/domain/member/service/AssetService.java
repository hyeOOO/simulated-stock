package toyproject.simulated_stock.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.auth.exception.AuthException;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.domain.member.dto.TotalAssetDto;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;
import toyproject.simulated_stock.domain.redis.dto.StockPriceDto;
import toyproject.simulated_stock.domain.redis.service.StockCacheService;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserStock;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserStockRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static toyproject.simulated_stock.api.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetService {

    private final MemberRepository memberRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserStockRepository userStockRepository;
    private final StockCacheService stockCacheService;

    //유저 자산 계산 로직
    @Transactional(readOnly = true)
    public TotalAssetDto calculateUserAssets(String memberKey) {
        //1. 유저 정보 불러오기
        Member member = memberRepository.findByMemberKey(memberKey)
                .orElseThrow(() -> new AuthException(MEMBER_NOT_FOUND));

        //2. 가용 자산 불러오기
        UserAccount userAccount = userAccountRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("바르지 않은 멤버 입니다. ")); //발생할 오류 고치기

        //3. 보유 주식 목록 가져오기
        List<UserStock> userStockList = userStockRepository.findByMemberId(memberKey);

        if (userStockList.isEmpty()) {
            return new TotalAssetDto(userAccount.getBalance(), userAccount.getBalance(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal stockTotal = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;

        for (UserStock userStock : userStockList) {
            String stockCode = userStock.getStockCode();
            StockPriceDto stockPriceDto = stockCacheService.getCachedStockPrice(stockCode);

            BigDecimal currentPrice = stockPriceDto != null
                    ? stockPriceDto.getCurrentPrice() //캐시된 현재가가 있을 떄
                    : BigDecimal.ZERO;

            //보유주식 총액 계산 (보유량 * 현재가)
            BigDecimal holdingTotal = currentPrice.multiply(BigDecimal.valueOf(userStock.getQuantity()));
            stockTotal = stockTotal.add(holdingTotal);

            //손익 계산 (현재가 - 매입가) * 보유량
            BigDecimal profit = (currentPrice.subtract(userStock.getAvgPrice())).multiply(BigDecimal.valueOf(userStock.getQuantity()));
            totalProfit = totalProfit.add(profit);
        }

        // 5. 총 자산 = 가용 자산 + 보유 주식 총액
        BigDecimal totalAssets = userAccount.getBalance().add(stockTotal);

        // 6. 손익률 = (손익 / 보유 주식 총액) * 100
        BigDecimal profitRate = stockTotal.compareTo(BigDecimal.ZERO) > 0
                ? totalProfit.divide(stockTotal, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))
                : BigDecimal.ZERO;

        // 7. 계산된 값들을 DTO에 담아 반환
        return new TotalAssetDto(totalAssets, userAccount.getBalance(), stockTotal, totalProfit, profitRate);
    }
}
