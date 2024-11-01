package toyproject.simulated_stock.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RankDto {
    private String id; // memberKey
    private String userName; // 닉네임
    private BigDecimal totalAssets; // 총자산
    private BigDecimal availableAsset; // 가용자산
    private BigDecimal stockTotal; // 보유 주식 총액
    private int rank;

    public RankDto(String id, String userName, BigDecimal totalAssets, BigDecimal availableAsset, BigDecimal stockTotal) {
        this.id = id;
        this.userName = userName;
        this.totalAssets = totalAssets;
        this.availableAsset = availableAsset;
        this.stockTotal = stockTotal;
    }

    public void setRank(int rank){
        this.rank = rank;
    }
}
