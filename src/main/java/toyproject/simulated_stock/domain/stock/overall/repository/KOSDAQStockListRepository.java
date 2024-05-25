package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.KOSDAQStockList;

public interface KOSDAQStockListRepository extends JpaRepository<KOSDAQStockList, Long> {

}
