package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;

public interface StockIndexRepository extends JpaRepository<StockIndex, Long> {
}
