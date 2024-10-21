package toyproject.simulated_stock.domain.stock.detail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.stock.detail.dto.StockBasicInfoDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockInvestorsDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsByPeriodDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;
import toyproject.simulated_stock.domain.stock.detail.option.QuotationsByPeriodOption;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

@Tag(name = "StockDetail", description = "StockDetail 관련 API 입니다. ")
@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
@Slf4j
public class DetailStockController {
    private final DetailStockService detailStockService;

    //주식 현재가 시세
    @Operation(summary = "주식 현재가 시세", description = "특정 주식 종목의 현재가 시세입니다. ")
    @GetMapping("/quotations/{stockCode}")
    public ResponseEntity<StockQuotationsDto> getStockQuotations(@PathVariable("stockCode") String stockCode){
        StockQuotationsDto response = detailStockService.getQuotations(stockCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //주식 현재가 투자자
    @Operation(summary = "주식 현재가 투자자", description = "특정 주식 종목의 현재가 투자자입니다. ")
    @GetMapping("/investors/{stockCode}")
    public ResponseEntity<StockInvestorsDto> getStockInvestors(@PathVariable("stockCode") String stockCode){
        StockInvestorsDto response = detailStockService.getInvestors(stockCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //국내 주식 기간별 시세(월/주/월/년)
    @Operation(summary = "국내 주식 기간별 시세(일/주/월/년)", description = "특정 국내 주식 종목의 기간별 시세입니다. ")
    @GetMapping("/quotations/{stockCode}/period")
    public ResponseEntity<StockQuotationsByPeriodDto> getStockQuotationsByPeriod(
            @PathVariable("stockCode") String stockCode,
            @ModelAttribute QuotationsByPeriodOption option){
            StockQuotationsByPeriodDto response = detailStockService.getQuotationsByPeriod(stockCode, option);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //종목 기본 정보
    @Operation(summary = "주식 기본 정보", description = "특정 주식 종목의 기본 정보입니다. ")
    @GetMapping("/info/{stockCode}")
    public String getStockDetail(@PathVariable String stockCode, Model model) {
        StockQuotationsDto stockQuotations = detailStockService.getQuotations(stockCode);

        model.addAttribute("stockQuotations", stockQuotations.getOutput());
        return "stock/stockDetail";
    }

    //
}
