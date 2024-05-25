package toyproject.simulated_stock.domain.stock.overall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="KOSDAQ_STOCK_LIST")
public class KOSDAQStockList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String basDt; //기준일자
    private String srtnCd; //단축코드
    private String isinCd; //ISIN코드
    private String itmsNm; //종목명
    private String mrktCtg; //시장구분
    private String clpr; //종가
    private String vs; //대비
    private String fltRt; //등락률
    private String mkp; //시가
    private String hipr; //고가
    private String lopr; //저가
    private String trqu; //거래량
    private String trPrc; //거래대금
    private String lstgStCnt; //상장주식수
    private String mrktTotAmt; //시가총액
}
