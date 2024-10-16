package toyproject.simulated_stock.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalAssetDto {
    private BigDecimal totalAssets;   // 총 자산
    private BigDecimal availableAsset;  // 가용 자산
    private BigDecimal stockTotal;      // 보유 주식 총액
    private BigDecimal profit;          // 손익
    private BigDecimal profitRate;      // 손익률
}
