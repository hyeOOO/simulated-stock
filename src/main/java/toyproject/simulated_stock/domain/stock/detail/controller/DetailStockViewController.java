package toyproject.simulated_stock.domain.stock.detail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.simulated_stock.domain.stock.detail.dto.StockBasicInfoDto;
import toyproject.simulated_stock.domain.stock.detail.service.DetailStockService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/stock")
@Slf4j
public class DetailStockViewController {

    private final DetailStockService detailStockService;

    // 종목 상세 페이지 렌더링
    @GetMapping("/{stockCode}")
    public String getStockDetailPage(@PathVariable("stockCode") String stockCode,
                                     @RequestParam("marketCategory") String marketCategory,
                                     Model model) {
        // 데이터베이스에서 종목에 대한 정보 조회
        StockBasicInfoDto stockInfo = detailStockService.getStockBasicInfo(stockCode, marketCategory);

        // 조회한 정보를 모델에 추가하여 뷰로 전달
        model.addAttribute("stockInfo", stockInfo);
        return "stock/stockdetail";
    }
}
