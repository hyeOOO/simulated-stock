package toyproject.simulated_stock.domain.stock.overall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.stock.overall.dto.StockIndexResponseDto;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.mapper.StockIndexMapper;
import toyproject.simulated_stock.domain.stock.overall.mapper.StockListMapper;
import toyproject.simulated_stock.domain.stock.overall.service.OverallStockService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class OverallStockController {

    private final StockListMapper stockListMapper;
    private final OverallStockService overallStockService;
    private final StockIndexMapper stockIndexMapper;

    @GetMapping("/list")
    public ResponseEntity<List<StockListResponseDto>> getListOfStock(){
        List<StockList> stockList = overallStockService.getStockList();
        List<StockListResponseDto> response = stockListMapper.stockListToStockListResponseDto(stockList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/index")
    public ResponseEntity<List<StockIndexResponseDto>> getListOfStockIndex(){
        List<StockIndex> stockIndexList = overallStockService.getStockIndex();
        List<StockIndexResponseDto> response = stockIndexMapper.stockIndexToStockIndexResponseDto(stockIndexList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
