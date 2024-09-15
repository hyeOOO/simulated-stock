package toyproject.simulated_stock.domain.stock.overall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OverallStockViewController {

    private final StockListRepository stockListRepository;

    @GetMapping("/stock-view")
    public String viewStocks(Model model) {
        List<StockList> stocks = stockListRepository.findAll();
        model.addAttribute("stocks", stocks);
        return "stock/stocklist"; // Thymeleaf 템플릿 이름
    }
}
