package toyproject.simulated_stock.domain.stock.order.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.api.exception.ExceptionCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Getter
public class UserStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;         // 사용자 ID
    private String stockCode;      // 주식 종목 코드
    private String stockName;      // 주식 종목 이름
    private int quantity;          // 보유 주식 수량
    private BigDecimal avgPrice;  // 매수 평균가

    private BigDecimal minPrice; // 최소 매수가
    private BigDecimal maxPrice; // 최대 매수가
    @ColumnDefault("0")
    private BigDecimal totalPrice; //매수시 가격과 개수에 따른 누적합

    @Enumerated(EnumType.STRING)
    private MarketType mrtgType; // KOSPI, KOSDAQ, KONEX

    private LocalDateTime lastUpdated;    // 마지막으로 업데이트된 시간

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;  // 유저 계좌 정보와의 연관 관계

    // 생성 메소드 (정적 팩토리 메소드)
    public static UserStock createUserStock(String memberId, String stockCode, String stockName, MarketType marketType, UserAccount userAccount) {
        UserStock userStock = new UserStock();
        userStock.memberId = memberId;
        userStock.stockCode = stockCode;
        userStock.stockName = stockName;
        userStock.userAccount = userAccount;
        userStock.quantity = 0;  // 초기 수량
        userStock.avgPrice = BigDecimal.ZERO;  // 초기 평균가
        userStock.minPrice = null;  // 초기 최저가
        userStock.maxPrice = null;  // 초기 최고가
        userStock.mrtgType = marketType;
        userStock.totalPrice = BigDecimal.ZERO;
        return userStock;
    }

    // 주식 매수 시 평균가, 수량 및 최소/최대 가격 업데이트
    public void buy(int newQuantity, BigDecimal newPrice) {
        // 평균가 계산
        BigDecimal totalAmount = avgPrice.multiply(BigDecimal.valueOf(quantity));
        BigDecimal newAmount = newPrice.multiply(BigDecimal.valueOf(newQuantity));
        BigDecimal totalQuantity = BigDecimal.valueOf(quantity + newQuantity);
        this.avgPrice = (totalAmount.add(newAmount)).divide(totalQuantity, RoundingMode.HALF_UP);

        // 수량 업데이트
        this.quantity += newQuantity;

        // 최소/최대 가격 업데이트
        updateMinMaxPrice(newPrice);

        BigDecimal newTotalAmount = newPrice.multiply(BigDecimal.valueOf(quantity));  // 새로 매수한 주식의 총 가격
        // 누적 총 가격 업데이트 (이전 가격 + 새로 매수한 가격)
        this.totalPrice = this.totalPrice.add(newTotalAmount);
    }

    // 매수 시 최소/최대 가격 갱신 메소드
    public void updateMinMaxPrice(BigDecimal price) {
        // 처음 매수하는 경우 null 값을 초기화
        if (minPrice == null || price.compareTo(minPrice) < 0) {
            minPrice = price;
        }
        if (maxPrice == null || price.compareTo(maxPrice) > 0) {
            maxPrice = price;
        }
    }

    // 매도 처리
    public void sellStock(int quantity, BigDecimal price) {
        if (this.quantity < quantity) {
            throw new BusinessLogicException(ExceptionCode.NOT_ENOUGH_STOCK);
        }
        this.quantity -= quantity;

        // 매도 수량에 해당하는 매수 금액 차감 (평균 매수가 기준)
        BigDecimal amountToDeduct = this.avgPrice.multiply(BigDecimal.valueOf(quantity));
        this.totalPrice = this.totalPrice.subtract(amountToDeduct);

        // 최소/최대 가격 갱신 (필요시)
        //updateMinMaxPrice(price);
    }
}
