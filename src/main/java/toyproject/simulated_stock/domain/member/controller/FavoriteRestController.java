package toyproject.simulated_stock.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.member.service.FavoriteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteRestController {

    private final FavoriteService favoriteService;

    @GetMapping("/check")
    public boolean isFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        return favoriteService.isFavorite(userId, stockCode);
    }

    @PostMapping("/add")
    public String addFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        favoriteService.addFavorite(userId, stockCode);
        return "즐겨찾기에 추가되었습니다.";
    }

    @PostMapping("/remove")
    public String removeFavorite(@RequestParam String userId, @RequestParam String stockCode) {
        favoriteService.removeFavorite(userId, stockCode);
        return "즐겨찾기에서 삭제되었습니다.";
    }
}
