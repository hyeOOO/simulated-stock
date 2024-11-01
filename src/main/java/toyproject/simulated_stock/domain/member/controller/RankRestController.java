package toyproject.simulated_stock.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.member.dto.RankDto;
import toyproject.simulated_stock.domain.member.dto.response.RankResponse;
import toyproject.simulated_stock.domain.member.service.AssetService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name="Rank", description = "유저 랭킹 관련 API 입니다. ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankRestController {
    private final AssetService assetService;

    // 로그인된 유저가 랭킹 접근 시 유저의 랭킹과 top 10 랭킹 리스트 불러오기
    @Operation(summary = "특정 유저 랭킹 정보", description = "특정 유저의 자산 정보 랭킹을 불러옵니다. ")
    @GetMapping("/{memberKey}")
    public ResponseEntity<RankResponse> getUserRank(@PathVariable String memberKey){
        List<RankDto> allRanks = assetService.getAllUserRank();

        // 전체 순위 매기기
        for (int i = 0; i < allRanks.size(); i++) {
            allRanks.get(i).setRank(i + 1);
        }

        // Top 10 추출
        List<RankDto> top10 = allRanks.stream()
                .limit(10)
                .collect(Collectors.toList());

        // 특정 유저의 랭킹 정보 찾기
        RankDto userRank = allRanks.stream()
                .filter(rank -> rank.getId().equals(memberKey))
                .findFirst()
                .orElse(null);

        return ResponseEntity.ok(new RankResponse(top10, userRank));
    }

    // 게스트가 랭킹 접근 시 top 10 랭킹 리스트 불러오기
//    @Operation(summary = "유저 랭킹 정보", description = "유저의 자산 정보 랭킹을 불러옵니다. ")
//    @GetMapping("/")
//    public ResponseEntity<> getGuestRank(){
//        // 유저 랭킹 정보
//        List<RankDto> rankDto = assetService.getAllUserRank();
//
//        return ResponseEntity.ok()
//    }
}
