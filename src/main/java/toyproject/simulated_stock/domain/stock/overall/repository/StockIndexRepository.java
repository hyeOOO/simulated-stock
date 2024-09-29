package toyproject.simulated_stock.domain.stock.overall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;

import java.util.List;
import java.util.Optional;

public interface StockIndexRepository extends JpaRepository<StockIndex, Long> {
    Optional<List<StockIndex>> findByIdxCsfAndIdxNm(String idxCsf, String idxNm);
}
