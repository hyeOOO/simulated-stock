package toyproject.simulated_stock.domain.redis.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toyproject.simulated_stock.domain.redis.dto.StockPriceDto;
import toyproject.simulated_stock.domain.redis.service.StockCacheService;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;
import toyproject.simulated_stock.domain.stock.overall.service.OverallStockService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockPriceScheduler {

    private final StockCacheService stockCacheService;
    private final OverallStockService overallStockService;
    private final DetailStockService detailStockService;  // 실제 주식 시세를 가져오는 서비스
    private final StockListRepository stockListRepository;

    //1분마다 실행하는 스케쥴러
    // 1분마다 주식 시세를 업데이트하는 스케줄러 (거래량에 따라 일부 종목은 덜 자주 갱신)
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateStockPrices() {
        List<String> stockCodes = overallStockService.getStockCodeListAll();

        // 요청을 분할하여, 1초마다 10개의 종목만 요청 (속도 조절)
        int batchSize = 2;
        for (int i = 0; i < stockCodes.size(); i += batchSize) {
            List<String> batch = stockCodes.subList(i, Math.min(i + batchSize, stockCodes.size()));

            batch.forEach(stockCode -> {
                try {
                    // 현재가 요청 후 캐싱
                    StockQuotationsDto stockData = detailStockService.getQuotations(stockCode);
                    List<StockList> stockList = stockListRepository.findBysrtnCd(stockCode);
                    StockPriceDto stockPriceDto = StockPriceDto.convertToStockPriceDto(stockCode, stockData, stockList.get(0));
                    stockCacheService.updateStockPrice(stockCode, stockPriceDto);  // 변환된 데이터를 전달

                    // 요청 간 간격 두기 (너무 많은 요청 방지)
                    Thread.sleep(1000);  // 1초 지연 시간
                } catch (Exception e) {
                    System.err.println("Error while fetching stock price for: " + stockCode + ". Error: " + e.getMessage());
                }
            });

            // 요청 간 간격을 더 길게 두어 API 제한을 준수
            try {
                Thread.sleep(2000);  // 2초 대기 (조정 가능)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
