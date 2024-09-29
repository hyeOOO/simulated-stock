package toyproject.simulated_stock.domain.stock.overall.dto;

import lombok.Data;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;

@Data
public class StockIndexResponseDto {
    private String lsYrEdVsFltRt; //전년 말 대비 등략률
    private String basPntm; //기준 포인트 - 특정 지수르르 측정하기 위한 기준이 되는 포인트 값
    private String basIdx; //기준 지수 - 특정 기준일을 기준으로 계산된 지수의 값
    private String basDt; //기준 일자
    private String idxCsf; //지수 분류
    private String idxNm; //지수명
    private String epyItmsCnt; //편입 종목 수 - 해당 지수에 포함된 종목의 수
    private String clpr; //종가 - 해당 일자의 거래가 종료된 시점의 가격
    private String vs; //전일 대비 등락
    private String fltRt; //전일 대비 등락률
    private String mkp; //시가
    private String hipr; //고가
    private String lopr; //저가
    private String trqu; //거래량
    private String trPrc; //거래대금
    private String lstgMrktTotAmt; //상장 시가총액
    private String lsYrEdVsFltRg; //전년도 대비 등락 폭
    private String yrWRcrdHgst; //연중 최고가
    private String yrWRcrdHgstDt; //연중 최고가 기록일
    private String yrWRcrdLwst; //연중 최고가
    private String yrWRcrdLwstDt; //연중 최저가 기록일

    // == fromEntity 메소드 추가 == //
    public static StockIndexResponseDto fromEntity(StockIndex marketIndex) {
        StockIndexResponseDto dto = new StockIndexResponseDto();
        dto.setLsYrEdVsFltRt(marketIndex.getLsYrEdVsFltRt());
        dto.setBasPntm(marketIndex.getBasPntm());
        dto.setBasIdx(marketIndex.getBasIdx());
        dto.setBasDt(marketIndex.getBasDt());
        dto.setIdxCsf(marketIndex.getIdxCsf());
        dto.setIdxNm(marketIndex.getIdxNm());
        dto.setEpyItmsCnt(marketIndex.getEpyItmsCnt());
        dto.setClpr(marketIndex.getClpr());
        dto.setVs(marketIndex.getVs());
        dto.setFltRt(marketIndex.getFltRt());
        dto.setMkp(marketIndex.getMkp());
        dto.setHipr(marketIndex.getHipr());
        dto.setLopr(marketIndex.getLopr());
        dto.setTrqu(marketIndex.getTrqu());
        dto.setTrPrc(marketIndex.getTrPrc());
        dto.setLstgMrktTotAmt(marketIndex.getLstgMrktTotAmt());
        dto.setLsYrEdVsFltRg(marketIndex.getLsYrEdVsFltRg());
        dto.setYrWRcrdHgst(marketIndex.getYrWRcrdHgst());
        dto.setYrWRcrdHgstDt(marketIndex.getYrWRcrdHgstDt());
        dto.setYrWRcrdLwst(marketIndex.getYrWRcrdLwst());
        dto.setYrWRcrdLwstDt(marketIndex.getYrWRcrdLwstDt());
        return dto;
    }
}
