package toyproject.simulated_stock.domain.stock.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // Member의 id로 조회하는 메서드로 수정
    Optional<UserAccount> findByMemberId(Long memberId);
}
