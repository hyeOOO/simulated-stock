package toyproject.simulated_stock.domain.stock.order.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import toyproject.simulated_stock.domain.stock.order.entitiy.MarketType;
import toyproject.simulated_stock.domain.stock.order.entitiy.OrderStatus;
import toyproject.simulated_stock.domain.stock.order.entitiy.OrderType;
import toyproject.simulated_stock.domain.stock.order.entitiy.StockOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockOrderListDto {
    private String stockCode; // 주식 코드
    private String stockName; // 주식 이름
    private int quantity;     // 주문 수량
    private BigDecimal price; // 주문 가격
    private MarketType mrtgType; //KOSPI, KOSDAQ, KONEX
    private LocalDateTime orderDate; // 주문 날짜
    private OrderType orderType; // 주문 타입 (BUY, SELL)
    private OrderStatus orderStatus; // 주문 상태 (PENDING, COMPLETED, CANCELLED)

    // 생성자 또는 정적 팩토리 메서드로 쉽게 변환할 수 있도록 추가
    public static StockOrderListDto fromEntity(StockOrder stockOrder) {
        StockOrderListDto dto = new StockOrderListDto();
        dto.stockCode = stockOrder.getStockCode();
        dto.stockName = stockOrder.getStockName();
        dto.quantity = stockOrder.getQuantity();
        dto.price = stockOrder.getPrice();
        dto.orderType = stockOrder.getOrderType();
        dto.mrtgType = stockOrder.getMrtgType();
        dto.orderStatus = stockOrder.getOrderStatus();
        dto.orderDate = stockOrder.getOrderDate();
        return dto;
    }
}
