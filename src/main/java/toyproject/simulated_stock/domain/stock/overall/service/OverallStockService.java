package toyproject.simulated_stock.domain.stock.overall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockIndexRepository;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OverallStockService {
    private final StockListRepository stockListRepository;
    private final StockIndexRepository stockIndexRepository;

    @Scheduled(cron = "0 0/5 * * * *") // 5분마다 실행
    public List<StockList> getStockList(){
        List<StockList> stockLists = stockListRepository.findAll();

        String recentBasDt = stockLists.get(0).getBasDt();

        List<StockList> recentStockList = stockLists.stream().filter(e -> e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockList;
    }

    public List<StockIndex> getStockIndex(){
        List<StockIndex> stockIndices = stockIndexRepository.findAll();

        String recentBasDt = stockIndices.get(0).getBasDt();

        List<StockIndex> recentStockIndexList = stockIndices.stream().filter(e->e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockIndexList;
    }
}
