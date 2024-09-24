package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

import java.util.Optional;

public interface StockListRepository extends JpaRepository<StockList, Long> {

    Optional<StockList> findBySrtnCdAndMrktCtg(String srtnCd, String mrktCtg);
}
