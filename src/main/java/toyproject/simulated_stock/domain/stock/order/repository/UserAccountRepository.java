package toyproject.simulated_stock.domain.stock.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUserId(String userId);
}
