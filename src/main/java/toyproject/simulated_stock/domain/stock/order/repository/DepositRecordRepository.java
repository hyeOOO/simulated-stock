package toyproject.simulated_stock.domain.stock.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.order.entitiy.DepositRecord;
public interface DepositRecordRepository extends JpaRepository<DepositRecord, Long> {
}