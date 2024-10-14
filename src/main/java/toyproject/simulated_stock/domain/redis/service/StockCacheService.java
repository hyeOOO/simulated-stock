package toyproject.simulated_stock.domain.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import toyproject.simulated_stock.domain.redis.dto.StockPriceDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StockCacheService {

    private final RedisTemplate<String, StockPriceDto> redisTemplate;
    private final DetailStockService detailStockService;

    // 캐싱된 주식 가격을 가져오는 메소드
    public StockPriceDto getCachedStockPrice(String stockCode) {
        ValueOperations<String, StockPriceDto> valueOps = redisTemplate.opsForValue();
        StockPriceDto cachedPrice = valueOps.get(stockCode);

        // 캐시된 데이터가 있으면 사용, 없으면 API 호출하여 갱신
        if (cachedPrice != null) {
            return cachedPrice;
        } else {
            StockQuotationsDto stockData = detailStockService.getQuotations(stockCode);
            StockPriceDto newPrice = StockPriceDto.convertToStockPriceDto(stockCode, stockData);  // 변환
            updateStockPrice(stockCode, newPrice); // 캐시에 저장
            return newPrice;
        }
    }

    // 주식 시세를 캐시에 업데이트하는 메소드
    // 캐시 업데이트 메소드에서 변환을 추가
    public void updateStockPrice(String stockCode, StockPriceDto stockPriceDto) {
        ValueOperations<String, StockPriceDto> valueOps = redisTemplate.opsForValue();
        valueOps.set(stockCode, stockPriceDto, 1, TimeUnit.MINUTES); // 1분 동안 캐시 유지
    }
}
