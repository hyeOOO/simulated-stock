package toyproject.simulated_stock.domain.stock.overall.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.service.SearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchRestController {

    private final SearchService searchService;

    @GetMapping
    public List<StockListResponseDto> searchStocks (
            @RequestParam String keyword,
            @RequestParam(required = false) String marketType
    ) {
        return searchService.searchStocks(keyword, marketType);
    }
}
