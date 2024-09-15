package toyproject.simulated_stock.domain.stock.detail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.stock.detail.dto.StockInvestorsDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsByPeriodDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;
import toyproject.simulated_stock.domain.stock.detail.option.QuotationsByPeriodOption;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
@Slf4j
public class DetailStockController {
    private final DetailStockService detailStockService;
    private final StockListRepository stockListRepository;

    //주식 현재가 시세
    @GetMapping("/quotations/{stockCode}")
    public ResponseEntity<StockQuotationsDto> getStockQuotations(@PathVariable("stockCode") String stockCode){
        StockQuotationsDto response = detailStockService.getQuotations(stockCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //주식 현재가 투자자
    @GetMapping("/investors/{stockCode}")
    public ResponseEntity<StockInvestorsDto> getStockInvestors(@PathVariable("stockCode") String stockCode){
        StockInvestorsDto response = detailStockService.getInvestors(stockCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //국내 주식 기간별 시세(월/주/월/년)
    @GetMapping("/quotations/{stockCode}/period")
    public ResponseEntity<StockQuotationsByPeriodDto> getStockQuotationsByPeriod(@PathVariable("stockCode") String stockCode, QuotationsByPeriodOption option){
        StockQuotationsByPeriodDto response = detailStockService.getQuotationsByPeriod(stockCode, option);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 종목 상세 페이지 이동
}
