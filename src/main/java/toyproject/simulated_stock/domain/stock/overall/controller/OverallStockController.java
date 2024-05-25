package toyproject.simulated_stock.domain.stock.overall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.KOSDAQStockList;
import toyproject.simulated_stock.domain.stock.overall.mapper.StockListMapper;
import toyproject.simulated_stock.domain.stock.overall.service.OverallStockService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class OverallStockController {

    private final StockListMapper stockListMapper;
    private final OverallStockService overallStockService;

    @GetMapping("/list/KOSDAQ")
    public ResponseEntity<List<StockListResponseDto>> getListOfKOSDAQ(){
        List<KOSDAQStockList> kosdaqStockLists = overallStockService.getKOSDAQStockList();
        List<StockListResponseDto> response = stockListMapper.KOSDAQStockListsToStockListsResponseDtos(kosdaqStockLists);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
