package toyproject.simulated_stock.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.member.dto.UserStockListDto;
import toyproject.simulated_stock.domain.member.service.AssetService;

import java.util.List;

@Tag(name = "MyPage", description = "MyPage 관련 API 입니다. ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myPage")
public class MyPageRestController {

    private final AssetService assetService;

    // 특정 회원의 보유 주식을 DTO로 변환하여 가져오기
    @Operation(summary = "보유 종목 리스트 조회", description = "특정 사용자의 보유중인 종목 리스트를 조회합니다. ")
    @GetMapping("/{memberKey}/userStock")
    public List<UserStockListDto> getUserStocks(@PathVariable String memberKey){
        return assetService.getUserStockList(memberKey);
    }

    // 특정 회원과 마켓종류에 따른 보유 주식을 DTO로 변환하여 가져오기
    @Operation(summary = "마켓별 보유 종목 리스트 조회", description = "특정 사용자의 마켓별 보유중인 종목 리스트를 조회합니다. ")
    @GetMapping("/{memberKey}/{market}/userStock")
    public List<UserStockListDto> getUserHaveMarketStock(@PathVariable String memberKey, @PathVariable String market){
        return assetService.getUserStockList(memberKey, market);
    }

    // 자산 초기화
    @Operation(summary = "자산 초기화", description = "특정 사용자의 자산을 초기화합니다. ")
    @DeleteMapping("/{memberKey}/reset")
    public String resetAsset(@PathVariable String memberKey){
        assetService.resetAccount(memberKey);
        return "초기화 완료";
    }
}
