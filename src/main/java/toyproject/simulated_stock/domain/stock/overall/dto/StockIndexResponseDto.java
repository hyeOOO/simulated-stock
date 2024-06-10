package toyproject.simulated_stock.domain.stock.overall.dto;

import lombok.Data;

@Data
public class StockIndexResponseDto {
    private String lsYrEdVsFltRt;
    private String basPntm;
    private String basIdx;
    private String basDt;
    private String idxCsf;
    private String idxNm;
    private String epyItmsCnt;
    private String clpr;
    private String vs;
    private String fltRt;
    private String mkp;
    private String hipr;
    private String lopr;
    private String trqu;
    private String trPrc;
    private String lstgMrktTotAmt;
    private String lsYrEdVsFltRg;
    private String yrWRcrdHgst;
    private String yrWRcrdHgstDt;
    private String yrWRcrdLwst;
    private String yrWRcrdLwstDt;
}
