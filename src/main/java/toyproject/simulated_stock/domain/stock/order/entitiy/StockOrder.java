package toyproject.simulated_stock.domain.stock.order.entitiy;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class StockOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private String stockCode; // 주식 코드
    private String stockName; // 주식 이름
    private int quantity;     // 주문 수량
    private BigDecimal price; // 주문 가격
    private MarketType mrtgType; //KOSPI, KOSDAQ, KONEX
    private LocalDateTime orderDate; // 주문 날짜

    @Enumerated(EnumType.STRING)
    private OrderType orderType; // 주문 타입 (BUY, SELL)

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태 (PENDING, COMPLETED, CANCELLED)

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;  // 유저 계좌 정보와의 연관 관계

    //==생성 메소드==//
    public static StockOrder createOrder(String memberId, String stockCode, String stockName, MarketType mrtgType, int quantity, BigDecimal price, OrderType orderType, UserAccount userAccount) {
        StockOrder stockOrder = new StockOrder();
        stockOrder.memberId = memberId;
        stockOrder.stockCode = stockCode;
        stockOrder.stockName = stockName;
        stockOrder.mrtgType = mrtgType;
        stockOrder.quantity = quantity;
        stockOrder.price = price;
        stockOrder.orderType = orderType;
        stockOrder.orderDate = LocalDateTime.now(); // 주문 시간은 생성 시점으로 설정
        stockOrder.orderStatus = OrderStatus.COMPLETED;
        stockOrder.userAccount = userAccount;
        return stockOrder;
    }}
