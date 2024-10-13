package toyproject.simulated_stock.domain.stock.order.dto;

import lombok.Data;
import toyproject.simulated_stock.domain.stock.order.entitiy.MarketType;

import java.math.BigDecimal;

@Data
public class StockOrderRequestDto {
    private String memberId;
    private String stockCode;
    private String stockName;
    private int quantity;
    private BigDecimal price;
    private MarketType mrtgType; //KOSPI, KOSDAQ, KONEX
}
