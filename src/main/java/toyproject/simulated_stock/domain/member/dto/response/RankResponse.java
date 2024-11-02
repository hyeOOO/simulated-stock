package toyproject.simulated_stock.domain.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.simulated_stock.domain.member.dto.RankDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class RankResponse {
    private List<RankDto> topRanks; // 상위 10명의 랭킹 정보
    private RankDto userRank;       // 특정 유저의 랭킹 정보 (선택적)

    public RankResponse(List<RankDto> topRanks) {
        this.topRanks = topRanks;
    }

    public RankResponse(List<RankDto> topRanks, RankDto userRank) {
        this.topRanks = topRanks;
        this.userRank = userRank;
    }
}