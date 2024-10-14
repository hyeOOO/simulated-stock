package toyproject.simulated_stock.domain.stock.overall.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.api.exception.ExceptionCode;
import toyproject.simulated_stock.domain.stock.overall.dto.StockIndexResponseDto;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockIndexRepository;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        // 최근 일자의 데이터를 거래량 기준으로 정렬
        List<StockList> recentStockList = stockLists.stream()
                .filter(e -> e.getBasDt().equals(recentBasDt)) // 최근 기준일자와 동일한 데이터 필터링
                .sorted((stock1, stock2) -> Long.compare(Long.parseLong(stock2.getTrqu()), Long.parseLong(stock1.getTrqu()))) // 거래량 기준으로 내림차순 정렬
                .collect(Collectors.toList());

        return recentStockList; // 이제 전체 데이터를 반환
    }

    // 엔티티 -> DTO 변환 및 반환
    public List<StockListResponseDto> getStockListDto() {
        // 1. 모든 주식 리스트를 가져옵니다.
        List<StockList> stockLists = stockListRepository.findAll();

        // 2. 가장 최근의 basDt 값을 찾습니다.
        String recentBasDt = stockLists.get(0).getBasDt();

        // 3. 최근 basDt와 일치하는 항목들만 필터링하고 상위 10개만 선택합니다.
        List<StockList> recentStockList = stockLists.stream()
                .filter(e -> e.getBasDt().equals(recentBasDt))
                .sorted((stock1, stock2) -> Long.compare(Long.parseLong(stock2.getTrqu()), Long.parseLong(stock1.getTrqu()))) // 거래량 기준으로 정렬
                .limit(10) // 상위 10개만 선택
                .collect(Collectors.toList());

        // 4. 엔티티를 DTO로 변환합니다.
        return recentStockList.stream()
                .map(stock -> StockListResponseDto.fromEntity(stock))
                .collect(Collectors.toList());
    }

    // 엔티티 -> DTO 변환 및 반환
    public List<StockListResponseDto> getStockListAllDto() {
        // 1. 모든 주식 리스트를 가져옵니다.
        List<StockList> stockLists = stockListRepository.findAll();

        // 2. 가장 최근의 basDt 값을 찾습니다.
        String recentBasDt = stockLists.get(0).getBasDt();

        // 3. 최근 basDt와 일치하는 항목들만 필터링합니다.
        List<StockList> recentStockList = stockLists.stream()
                .filter(e -> e.getBasDt().equals(recentBasDt))
                .sorted((stock1, stock2) -> Long.compare(Long.parseLong(stock2.getTrqu()), Long.parseLong(stock1.getTrqu()))) // 거래량 기준으로 정렬
                .collect(Collectors.toList());

        // 4. 엔티티를 DTO로 변환합니다.
        return recentStockList.stream()
                .map(stock -> StockListResponseDto.fromEntity(stock))
                .collect(Collectors.toList());
    }

    //모든 주식에서 코드만 반환
    public List<String> getStockCodeListAll() {
        // 1. 모든 주식 리스트를 가져옵니다.
        List<StockList> stockLists = stockListRepository.findAll();

        // 2. 가장 최근의 basDt 값을 찾습니다.
        String recentBasDt = stockLists.get(0).getBasDt();

        // 3. 최근 basDt와 일치하는 항목들만 필터링하고 거래량 기준으로 정렬합니다.
        List<StockList> recentStockList = stockLists.stream()
                .filter(e -> e.getBasDt().equals(recentBasDt))
                .sorted((stock1, stock2) -> Long.compare(Long.parseLong(stock2.getTrqu()), Long.parseLong(stock1.getTrqu()))) // 거래량 기준으로 정렬
                .collect(Collectors.toList());

        // 4. 주식 종목 코드(stockCode)를 추출하여 리스트로 변환
        return recentStockList.stream()
                .map(StockList::getSrtnCd)  // StockList 엔티티에서 stockCode 추출
                .collect(Collectors.toList()); // 리스트로 변환하여 반환
    }

    public List<StockIndex> getStockIndex(){
        List<StockIndex> stockIndices = stockIndexRepository.findAll();

        String recentBasDt = stockIndices.get(0).getBasDt();

        List<StockIndex> recentStockIndexList = stockIndices.stream().filter(e->e.getBasDt().equals(recentBasDt)).collect(Collectors.toList());

        return recentStockIndexList;
    }

    // 특정 지수 분류와 지수명을 기준으로 지수 정보 조회
    @Transactional(readOnly = true)
    public StockIndexResponseDto getMarketIndexByType(String idxCsf, String idxNm) {
        List<StockIndex> marketIndices = stockIndexRepository.findByIdxCsfAndIdxNm(idxCsf, idxNm)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CANNOT_FOUND_STOCK_DATA));
        log.info("marketIndex={}", marketIndices);
        if (marketIndices.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.CANNOT_FOUND_STOCK_DATA);
        }

        // 가장 최신 날짜를 기준으로 데이터 선택
        StockIndex latestMarketIndex = marketIndices.stream()
                .max(Comparator.comparing(StockIndex::getBasDt)) // 기준 일자(basDt)를 기준으로 가장 최신 데이터 선택
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CANNOT_FOUND_STOCK_DATA));

        return StockIndexResponseDto.fromEntity(latestMarketIndex);
    }
}
