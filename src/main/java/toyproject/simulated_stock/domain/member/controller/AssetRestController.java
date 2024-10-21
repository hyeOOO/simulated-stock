package toyproject.simulated_stock.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.member.dto.TotalAssetDto;
import toyproject.simulated_stock.domain.member.service.AssetService;

@Tag(name = "Asset", description = "Asset 관련 API 입니다. ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetRestController {

    private final AssetService assetService;

    //특정 유저의 자산 정보 가져오기
    @Operation(summary = "특정 사용자의 자산 정보", description = "특정 사용자의 자산 정보(총자산, 가용자산, 보유주식 총액, 손익, 손익률입니다. ")
    @GetMapping("/{memberKey}")
    public ResponseEntity<TotalAssetDto> getUserAssets(@PathVariable String memberKey) {
        TotalAssetDto totalAssetDto = assetService.calculateUserAssets(memberKey);
        return ResponseEntity.ok(totalAssetDto);
    }
}
