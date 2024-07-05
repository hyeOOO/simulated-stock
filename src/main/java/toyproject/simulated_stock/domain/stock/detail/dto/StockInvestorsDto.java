package toyproject.simulated_stock.domain.stock.detail.dto;

import lombok.Data;

import java.util.List;

@Data
public class StockInvestorsDto {
    private List<Output> output = null;

    //@Data 어노테이션 빠뜨리면 에러
    @Data
    public static class Output{
        private String stck_bsop_date;
        private String stck_clpr;
        private String prdy_vrss;
        private String prdy_vrss_sign;
        private String prsn_ntby_qty;
        private String frgn_ntby_qty;
        private String orgn_ntby_qty;
        private String prsn_ntby_tr_pbmn;
        private String frgn_ntby_tr_pbmn;
        private String orgn_ntby_tr_pbmn;
        private String prsn_shnu_vol;
        private String frgn_shnu_vol;
        private String orgn_shnu_vol;
        private String prsn_shnu_tr_pbmn;
        private String frgn_shnu_tr_pbmn;
        private String orgn_shnu_tr_pbmn;
        private String prsn_seln_vol;
        private String frgn_seln_vol;
        private String orgn_seln_vol;
        private String prsn_seln_tr_pbmn;
        private String frgn_seln_tr_pbmn;
        private String orgn_seln_tr_pbmn;
    }
}
