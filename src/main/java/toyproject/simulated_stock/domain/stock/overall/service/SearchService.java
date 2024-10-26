package toyproject.simulated_stock.domain.stock.overall.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final StockListRepository stockListRepository;

    public List<StockListResponseDto> searchStocks(String keyword, String marketType) {
        List<StockList> stockList;
        if (marketType.equals("ALL") || marketType.isEmpty() || marketType == null) {
            stockList = stockListRepository.findByItmsNmContaining(keyword);
        } else {
            stockList = stockListRepository.findByItmsNmContainingAndMrktCtg(keyword, marketType);
        }

        return stockList.stream()
                .map(StockListResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
