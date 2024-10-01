package toyproject.simulated_stock.domain.stock.overall.repository.query;

import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

public interface QueryStockListRepository {
    StockList findLatestStockInfo(String stockCode, String marketCategory);
    String findLatestBaseDate(String stockCode, String marketCategory);
}
