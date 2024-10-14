package toyproject.simulated_stock.domain.redis.dto;

import lombok.Data;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StockPriceDto implements Serializable {

    private static final long serialVersionUID = 1L; // 직렬화 ID

    private String stockCode;      // 주식 코드
    private String stockName;      // 주식 이름
    private BigDecimal currentPrice; // 현재가 시세

    // 생성자 추가
    public StockPriceDto(String stockCode, String stockName, BigDecimal currentPrice) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }

    // 예시 변환 코드 (StockQuotationsDto -> StockPriceDto)
    public static StockPriceDto convertToStockPriceDto(String stockCode, StockQuotationsDto stockQuotationsDto) {
        String stockName = stockQuotationsDto.getOutput().getRprs_mrkt_kor_name();  // 주식 이름
        BigDecimal currentPrice = new BigDecimal(stockQuotationsDto.getOutput().getStck_prpr());  // 현재가 시세
        return new StockPriceDto(stockCode, stockName, currentPrice);  // 변환 후 반환
    }
}
