package toyproject.simulated_stock.domain.stock.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockOrderRequestDto {
    private String userId;
    private String stockCode;
    private String stockName;
    private int quantity;
    private BigDecimal price;
    private MrtgType mrtgType; //KOSPI, KOSDAQ, KONEX
}
