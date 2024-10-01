package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.query.QueryStockListRepository;

public interface StockListRepository extends JpaRepository<StockList, Long>, QueryStockListRepository {
}
