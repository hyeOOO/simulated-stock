package toyproject.simulated_stock.domain.stock.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserStockRequestDto {
    private String userId;
    private String stockCode;
    private String stockName;
    private int quantity;
    private BigDecimal avgPrice;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String mrtgType; //KOSPI, KOSDAQ, KONEX

}
