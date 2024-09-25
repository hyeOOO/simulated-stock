package toyproject.simulated_stock.domain.stock.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.stock.order.dto.StockOrderRequestDto;
import toyproject.simulated_stock.domain.stock.order.service.StockOrderService;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock/order")
public class OrderRestController {

    private final StockOrderService stockOrderService;

    //매수
    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody StockOrderRequestDto request) {
        stockOrderService.buyStock(request.getUserId(), request.getStockCode(), request.getQuantity(), request.getPrice());
        return ResponseEntity.ok("매수가 완료되었습니다.");
    }

    //매도
    /*@PostMapping
    public ResponseEntity<String> sellStock(@RequestBody StockOrderRequestDto request) {
        try {
            stockOrderService.sellStock(request.getUserId(), request.getStockCode(), request.getQuantity(), request.getPrice());
            return ResponseEntity.ok("매도가 완료되었습니다.");
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/
}
