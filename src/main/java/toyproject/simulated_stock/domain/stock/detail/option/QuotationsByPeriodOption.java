package toyproject.simulated_stock.domain.stock.detail.option;

import lombok.Data;

@Data
public class QuotationsByPeriodOption {
    private String startDate;
    private String endDate;
    private String periodCode;
    private String orgAdjPrc;
}
