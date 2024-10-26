package toyproject.simulated_stock.domain.stock.overall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;
import toyproject.simulated_stock.domain.stock.overall.service.OverallStockService;

import java.util.List;
import toyproject.simulated_stock.domain.stock.overall.service.SearchService;

@Controller
@RequiredArgsConstructor
public class OverallStockViewController {

    private final OverallStockService overallStockService;
    private final SearchService searchService;

    @GetMapping("/stock-view")
    public String viewStocks(Model model) {
        List<StockListResponseDto> stocks = overallStockService.getStockListDto();
        model.addAttribute("stocks", stocks);
        return "stock/stocklist"; // Thymeleaf 템플릿 이름
    }

    @GetMapping("/search")
    public String searchPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "marketType", required = false) String marketType,
            Model model) {

        List<StockListResponseDto> results = searchService.searchStocks(keyword, marketType);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        model.addAttribute("market", marketType);

        return "stock/searchresult"; // 검색 결과를 표시하는 HTML 페이지
    }
}
