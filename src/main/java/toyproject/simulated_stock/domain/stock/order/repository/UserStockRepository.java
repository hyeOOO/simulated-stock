package toyproject.simulated_stock.domain.stock.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserStock;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<UserStock, Long> {
    // 사용자 ID와 주식 종목 코드로 보유 주식 정보 조회
    Optional<UserStock> findByMemberIdAndStockCode(String memberId, String stockCode);

    // 사용자의 모든 보유 주식 조회
    List<UserStock> findByMemberId(String memberId);

    // 사용자의 모든 보유 주식 삭제
    void deleteByMemberId(String memberId);
}