package toyproject.simulated_stock.domain.stock.overall.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import toyproject.simulated_stock.domain.stock.overall.entity.QStockList;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

@Repository
@Primary
@RequiredArgsConstructor
public class QueryStockListRepositoryImpl implements QueryStockListRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 주어진 종목 코드와 시장 카테고리를 기준으로 최신 주식 정보를 가져오는 메서드
     * @param stockCode - 종목 코드
     * @param marketCategory - 시장 카테고리
     * @return - 최신 주식 정보
     */
    @Override
    public StockList findLatestStockInfo(String stockCode, String marketCategory) {
        QStockList stockList = QStockList.stockList; //QStockList 클래스 생성

        return queryFactory
                .selectFrom(stockList)
                .where(
                        stockList.srtnCd.eq(stockCode), // 종목 코드 일치 조건
                        stockList.mrktCtg.eq(marketCategory), // 시장 카테고리 일치 조건
                        stockList.basDt.eq(findLatestBaseDate(stockCode, marketCategory)) //가장 최신 날짜 조건
                )
                .fetchOne(); //단일 결과 반환
    }

    @Override
    public String findLatestBaseDate(String stockCode, String marketCategory) {
        QStockList stockList = QStockList.stockList;

        return queryFactory
                .select(stockList.basDt)
                .from(stockList)
                .where(
                        stockList.srtnCd.eq(stockCode),
                        stockList.mrktCtg.eq(marketCategory)
                )
                .orderBy(stockList.basDt.desc()) // 기준 날짜를 내림차순으로 정렬하여 가장 최근 날짜 찾기
                .limit(1) // 가장 최신 1개만 조회
                .fetchOne();
    }
}
