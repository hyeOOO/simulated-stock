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
        stockOrderService.buyStock(request.getMemberId(), request.getStockCode(), request.getQuantity(), request.getPrice());
        return ResponseEntity.ok("매수가 완료되었습니다.");
    }

    // 매도 기능
    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody StockOrderRequestDto request) {
        // 매도 로직 실행
        stockOrderService.sellStock(request.getMemberId(), request.getStockCode(), request.getQuantity(), request.getPrice());
        return ResponseEntity.ok("매도가 완료되었습니다.");
    }

    // 특정 사용자의 보유 주식 개수를 반환하는 API
    @GetMapping("/holding/{userId}/{stockCode}")
    public ResponseEntity<Integer> getStockHolding(@PathVariable String userId, @PathVariable String stockCode) {
        int holdingQuantity = stockOrderService.getHoldingStockQuantity(userId, stockCode);
        return ResponseEntity.ok(holdingQuantity);
    }
}
