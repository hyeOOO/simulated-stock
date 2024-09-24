package toyproject.simulated_stock.domain.stock.overall.dto;

import lombok.Data;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

@Data
public class StockListResponseDto {
    private Long stockId;
    private String basDt;
    private String srtnCd;
    private String isinCd;
    private String itmsNm;
    private String mrktCtg;
    private String clpr;
    private String vs;
    private String fltRt;
    private String mkp;
    private String hipr;
    private String lopr;
    private String trqu;
    private String trPrc;
    private String lstgStCnt;
    private String mrktTotAmt;

    // 엔티티 -> DTO 변환 메서드
    public static StockListResponseDto fromEntity(StockList entity) {
        StockListResponseDto dto = new StockListResponseDto();
        dto.setStockId(entity.getStockId());
        dto.setBasDt(entity.getBasDt());
        dto.setSrtnCd(entity.getSrtnCd());
        dto.setIsinCd(entity.getIsinCd());
        dto.setItmsNm(entity.getItmsNm());
        dto.setMrktCtg(entity.getMrktCtg());
        dto.setClpr(entity.getClpr());
        dto.setVs(entity.getVs());
        dto.setFltRt(entity.getFltRt());
        dto.setMkp(entity.getMkp());
        dto.setHipr(entity.getHipr());
        dto.setLopr(entity.getLopr());
        dto.setTrqu(entity.getTrqu());
        dto.setTrPrc(entity.getTrPrc());
        dto.setLstgStCnt(entity.getLstgStCnt());
        dto.setMrktTotAmt(entity.getMrktTotAmt());
        return dto;
    }
}
