package toyproject.simulated_stock.domain.redis.dto;

import lombok.Data;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;

import java.io.Serializable;
import java.math.BigDecimal;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

@Data
public class StockPriceDto implements Serializable {

    private static final long serialVersionUID = 1L; // 직렬화 ID

    private String stockCode;      // 주식 코드
    private String stockName;      // 주식 이름
    private BigDecimal currentPrice; // 현재가 시세
    private String priceChangeRate; // 등락률
    private String marketType; //시장 타입

    // 생성자 추가
    public StockPriceDto(String stockCode, String stockName, BigDecimal currentPrice, String priceChangeRate, String marketType) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.priceChangeRate = priceChangeRate;
        this.marketType = marketType;
    }

    // 예시 변환 코드 (StockQuotationsDto -> StockPriceDto)
    public static StockPriceDto convertToStockPriceDto(String stockCode, StockQuotationsDto stockQuotationsDto, StockList stockList) {
        String stockName = stockList.getItmsNm();  // 주식 이름
        BigDecimal currentPrice = new BigDecimal(stockQuotationsDto.getOutput().getStck_prpr());  // 현재가 시세
        String priceChangeRate = stockQuotationsDto.getOutput().getPrdy_ctrt();
        String marketType = stockQuotationsDto.getOutput().getRprs_mrkt_kor_name(); //시장 타입
        return new StockPriceDto(stockCode, stockName, currentPrice, priceChangeRate, marketType);  // 변환 후 반환
    }
}
