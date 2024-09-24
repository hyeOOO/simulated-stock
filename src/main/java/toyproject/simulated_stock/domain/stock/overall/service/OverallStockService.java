package toyproject.simulated_stock.domain.stock.overall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
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

    //@Scheduled(cron = "0 0/5 * * * *") // 5분마다 실행
    public List<StockList> getStockList(){
        List<StockList> stockLists = stockListRepository.findAll();

        String recentBasDt = stockLists.get(0).getBasDt();

        List<StockList> recentStockList = stockLists.stream().filter(e -> e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockList;
    }

    // 엔티티 -> DTO 변환 및 반환
    public List<StockListResponseDto> getStockListDto() {
        // 1. 모든 주식 리스트를 가져옵니다.
        List<StockList> stockLists = stockListRepository.findAll();

        // 2. 가장 최근의 basDt 값을 찾습니다.
        String recentBasDt = stockLists.get(0).getBasDt();

        // 3. 최근 basDt와 일치하는 항목들만 필터링합니다.
        List<StockList> recentStockList = stockLists.stream()
                .filter(e -> e.getBasDt().equals(recentBasDt))
                .collect(Collectors.toList());

        // 4. 엔티티를 DTO로 변환합니다.
        return recentStockList.stream()
                .map(stock -> StockListResponseDto.fromEntity(stock))
                .collect(Collectors.toList());
    }

    public List<StockIndex> getStockIndex(){
        List<StockIndex> stockIndices = stockIndexRepository.findAll();

        String recentBasDt = stockIndices.get(0).getBasDt();

        List<StockIndex> recentStockIndexList = stockIndices.stream().filter(e->e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockIndexList;
    }
}
