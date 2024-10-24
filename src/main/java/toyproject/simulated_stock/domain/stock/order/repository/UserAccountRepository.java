package toyproject.simulated_stock.domain.stock.order.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // Member의 id로 조회하는 메서드로 수정
    Optional<UserAccount> findByMemberId(Long memberId);

    // 사용자의 잔고 초기화
    @Modifying
    @Transactional
    @Query("UPDATE UserAccount ua SET ua.balance = :balance WHERE ua.member.id = :memberId")
    void updateBalanceByMemberId(Long memberId, BigDecimal balance);
}
