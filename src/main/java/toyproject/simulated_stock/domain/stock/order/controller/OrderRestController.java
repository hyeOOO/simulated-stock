package toyproject.simulated_stock.domain.stock.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.simulated_stock.domain.stock.order.dto.StockOrderListDto;
import toyproject.simulated_stock.domain.stock.order.dto.StockOrderRequestDto;
import toyproject.simulated_stock.domain.stock.order.service.StockOrderService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stock/order")
public class OrderRestController {

    private final StockOrderService stockOrderService;

    //매수
    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody StockOrderRequestDto request) {
        stockOrderService.buyStock(request.getMemberId(), request.getStockCode(), request.getStockName(), request.getQuantity(), request.getPrice(), request.getMrtgType());
        return ResponseEntity.ok("매수가 완료되었습니다.");
    }

    // 매도 기능
    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody StockOrderRequestDto request) {
        // 매도 로직 실행
        stockOrderService.sellStock(request.getMemberId(), request.getStockCode(), request.getStockName(), request.getQuantity(), request.getPrice());
        return ResponseEntity.ok("매도가 완료되었습니다.");
    }

    // 특정 사용자의 보유 주식 개수를 반환하는 API
    @GetMapping("/holding/{memberKey}/{stockCode}")
    public ResponseEntity<Integer> getStockHolding(@PathVariable String memberKey, @PathVariable String stockCode) {
        int holdingQuantity = stockOrderService.getHoldingStockQuantity(memberKey, stockCode);
        return ResponseEntity.ok(holdingQuantity);
    }

    // 특정 회원의 모든 주문 기록을 DTO로 변환하여 가져오기
    @GetMapping("/{memberKey}/all")
    public List<StockOrderListDto> getAllOrders(@PathVariable String memberKey) {
        return stockOrderService.getAllOrdersByMemberKey(memberKey);
    }

    // 특정 회원의 매수 기록을 DTO로 변환하여 가져오기
    @GetMapping("/{memberKey}/buy")
    public List<StockOrderListDto> getBuyOrders(@PathVariable String memberKey) {
        return stockOrderService.getBuyOrdersByMemberKey(memberKey);
    }

    // 특정 회원의 매도 기록을 DTO로 변환하여 가져오기
    @GetMapping("/{memberKey}/sell")
    public List<StockOrderListDto> getSellOrders(@PathVariable String memberKey) {
        return stockOrderService.getSellOrdersByMemberKey(memberKey);
    }
}
