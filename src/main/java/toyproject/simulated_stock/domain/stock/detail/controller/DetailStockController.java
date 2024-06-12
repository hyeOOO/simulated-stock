package toyproject.simulated_stock.domain.stock.detail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationDto;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class DetailStockController {
    private final DetailStockService detailStockService;

    @GetMapping("/quotations/{stockCode}")
    public ResponseEntity<StockQuotationDto> getStockQuotations(@PathVariable("stockCode") String stockCode){
        StockQuotationDto response = detailStockService.getQuotations(stockCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
