package toyproject.simulated_stock.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.member.service.FavoriteService;

@Tag(name = "Favorite", description = "Favorite 관련 API 입니다. ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteRestController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 체크", description = "특정 사용자가 특정 종목을 즐겨찾기하였는지 체크합니다. ")
    @GetMapping("/check")
    public boolean isFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        return favoriteService.isFavorite(userId, stockCode);
    }

    @Operation(summary = "즐겨찾기 추가", description = "특정 사용자가 특정 종목을 즐겨찾기에 추가합니다. ")
    @PostMapping("/add")
    public String addFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        favoriteService.addFavorite(userId, stockCode);
        return "즐겨찾기에 추가되었습니다.";
    }

    @Operation(summary = "즐겨찾기 제거", description = "특정 사용자가 특정 종목의 즐겨찾기를 제거합니다. ")
    @PostMapping("/remove")
    public String removeFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        favoriteService.removeFavorite(userId, stockCode);
        return "즐겨찾기에서 삭제되었습니다.";
    }
}
