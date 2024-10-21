package toyproject.simulated_stock.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.member.dto.UserStockListDto;
import toyproject.simulated_stock.domain.member.service.AssetService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
public class MyPageRestController {

    private final AssetService assetService;

    // 특정 회원의 보유 주식을 DTO로 변환하여 가져오기
    @GetMapping("/{memberKey}/userStock")
    public List<UserStockListDto> getUserStocks(@PathVariable String memberKey){
        return assetService.getUserStockList(memberKey);
    }

}
