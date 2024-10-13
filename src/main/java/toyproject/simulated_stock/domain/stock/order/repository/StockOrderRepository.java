package toyproject.simulated_stock.domain.stock.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.order.entitiy.OrderType;
import toyproject.simulated_stock.domain.stock.order.entitiy.StockOrder;

import java.util.List;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {

    //특정 회원의 전체 주문 기록 가져오기
    List<StockOrder> findByMemberId(String memberId);

    // 특정 회원의 매수(BUY) 또는 매도(SELL) 기록 가져오기
    List<StockOrder> findByMemberIdAndOrderType(String memberId, OrderType orderType);
}
