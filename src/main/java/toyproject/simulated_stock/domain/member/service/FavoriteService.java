package toyproject.simulated_stock.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.member.entity.Favorite;
import toyproject.simulated_stock.domain.member.repository.FavoriteRepository;
import toyproject.simulated_stock.domain.redis.dto.StockPriceDto;
import toyproject.simulated_stock.domain.redis.service.StockCacheService;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StockCacheService stockCacheService;

    @Transactional
    public boolean isFavorite(String userId, String stockCode) {
        return favoriteRepository.findByUserIdAndStockCode(userId, stockCode).isPresent();
    }

    @Transactional
    public void addFavorite(String userId, String stockCode, String marketType) {
        if (!isFavorite(userId, stockCode)) {
            Favorite favorite = Favorite.createFavorite(stockCode, userId, marketType);
            favoriteRepository.save(favorite);
        }
    }

    @Transactional
    public void removeFavorite(String userId, String stockCode) {
        favoriteRepository.deleteByUserIdAndStockCode(userId, stockCode);
    }

    @Transactional(readOnly = true)
    public List<StockPriceDto> getMemberFavoriteListByMarketType(String userId, String marketType) {
        List<Favorite> favoriteList = favoriteRepository.findByUserId(userId);

         if (marketType != null && !marketType.equals("ALL")) {
            favoriteList = favoriteList.stream()
                    .filter(favorite -> favorite.getMarketType().contains(marketType))
                    .collect(Collectors.toList());
        }

        //이 부분 수정이 필요할 수도 있을 듯
        List<StockPriceDto> favoriteStocks = favoriteList.stream()
                .map(favorite -> stockCacheService.getCachedStockPrice(favorite.getStockCode()))
                .collect(Collectors.toList());

            return favoriteStocks;
    }
}
