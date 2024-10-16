package toyproject.simulated_stock.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.member.dto.TotalAssetDto;
import toyproject.simulated_stock.domain.member.service.AssetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/asset")
public class AssetRestController {

    private final AssetService assetService;

    //특정 유저의 자산 정보 가져오기
    @GetMapping("/{memberKey}")
    public ResponseEntity<TotalAssetDto> getUserAssets(@PathVariable String memberKey) {
        TotalAssetDto totalAssetDto = assetService.calculateUserAssets(memberKey);
        return ResponseEntity.ok(totalAssetDto);
    }
}
