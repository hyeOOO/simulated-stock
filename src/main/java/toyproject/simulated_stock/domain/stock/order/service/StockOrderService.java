package toyproject.simulated_stock.domain.stock.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.domain.stock.order.entitiy.OrderType;
import toyproject.simulated_stock.domain.stock.order.entitiy.StockOrder;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;
import toyproject.simulated_stock.domain.stock.order.repository.StockOrderRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockOrderService {

    private final UserAccountRepository userAccountRepository;
    private final StockOrderRepository stockOrderRepository;

    //매수 로직
    @Transactional
    public void buyStock(String userId, String stockCode, int quantity, BigDecimal price) {
        //매수하려는 유저의 계좌
        UserAccount userAccount = userAccountRepository.findByUserId(userId);

        //가격 계산
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

        //잔액 확인 및 차감
        userAccount.withdraw(totalAmount);

        //주문 기록 생성
        // StockOrder 객체 생성은 생성 메소드 사용
        StockOrder stockOrder = StockOrder.createOrder(userId, stockCode, quantity, price, OrderType.BUY, userAccount);

        //주문 저장
        stockOrderRepository.save(stockOrder);
        userAccountRepository.save(userAccount);
    }
}
