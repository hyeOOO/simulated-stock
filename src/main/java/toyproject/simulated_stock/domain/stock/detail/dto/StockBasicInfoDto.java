package toyproject.simulated_stock.domain.stock.detail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

@Data
@Builder
public class StockBasicInfoDto {

    private String stockCode;    // 종목 단축 코드 (엔티티의 srtnCd에 해당)
    private String stockName;    // 종목명 (엔티티의 itmsNm에 해당)
    private String currentPrice; // 현재가 (엔티티의 clpr에 해당)
    private String changeRate;   // 등락률 (엔티티의 fltRt에 해당)
    private String openingPrice; // 시가 (엔티티의 mkp에 해당)
    private String highPrice;    // 고가 (엔티티의 hipr에 해당)
    private String lowPrice;     // 저가 (엔티티의 lopr에 해당)
    private String tradingVolume; // 거래량 (엔티티의 trqu에 해당)
    private String marketCap;    // 시가총액 (엔티티의 mrktTotAmt에 해당)
    private String lastUpdated;  // 데이터 마지막 업데이트 시간 (엔티티에 없을 수 있으므로, 별도 관리할 경우 사용)
    private String mrktCtg;

    public static StockBasicInfoDto convertEntityToDto(StockList stockList) {
        return StockBasicInfoDto.builder()
                .stockCode(stockList.getSrtnCd())       // 종목 단축 코드
                .stockName(stockList.getItmsNm())       // 종목명
                .currentPrice(stockList.getClpr())      // 현재가
                .changeRate(stockList.getFltRt())       // 등락률
                .openingPrice(stockList.getMkp())       // 시가
                .highPrice(stockList.getHipr())         // 고가
                .lowPrice(stockList.getLopr())          // 저가
                .tradingVolume(stockList.getTrqu())     // 거래량
                .marketCap(stockList.getMrktTotAmt())   // 시가총액
                .lastUpdated("2024-09-16")              // 필요한 경우 DB에서 관리되지 않는 추가 필드
                .mrktCtg(stockList.getMrktCtg())
                .build();
    }
}
