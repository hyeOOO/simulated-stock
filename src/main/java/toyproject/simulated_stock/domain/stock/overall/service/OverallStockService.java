package toyproject.simulated_stock.domain.stock.overall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.stock.overall.entity.KOSDAQStockList;
import toyproject.simulated_stock.domain.stock.overall.repository.KOSDAQStockListRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OverallStockService {
    private final KOSDAQStockListRepository kosdaqStockListRepository;

    public List<KOSDAQStockList> getKOSDAQStockList(){
        List<KOSDAQStockList> foundLists = kosdaqStockListRepository.findAll();

        String recentBasDt = foundLists.get(0).getBasDt();
        List<KOSDAQStockList> recentStockIndices = foundLists.stream().filter(e -> e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockIndices;
    }
}
