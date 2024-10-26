package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.query.QueryStockListRepository;

import java.util.List;

public interface StockListRepository extends JpaRepository<StockList, Long>, QueryStockListRepository {
    List<StockList> findBysrtnCd(String stockCode);

    List<StockList> findByItmsNmContaining(String itmsNm);

    List<StockList> findByItmsNmContainingAndMrktCtg(String itmsNm, String mrktCtg);
}
