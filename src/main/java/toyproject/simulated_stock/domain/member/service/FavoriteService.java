package toyproject.simulated_stock.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.member.entity.Favorite;
import toyproject.simulated_stock.domain.member.repository.FavoriteRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public boolean isFavorite(Long userId, String stockCode) {
        return favoriteRepository.findByUserIdAndStockCode(userId, stockCode).isPresent();
    }

    @Transactional
    public void addFavorite(Long userId, String stockCode) {
        if (!isFavorite(userId, stockCode)) {
            Favorite favorite = Favorite.createFavorite(stockCode, userId);
            favoriteRepository.save(favorite);
        }
    }

    @Transactional
    public void removeFavorite(Long userId, String stockCode) {
        favoriteRepository.deleteByUserIdAndStockCode(userId, stockCode);
    }
}
