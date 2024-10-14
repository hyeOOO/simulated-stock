package toyproject.simulated_stock.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.simulated_stock.domain.member.entity.Favorite;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    //특정 사용자가 특정 주식을 즐겨찾기에 가지고 있는지 확인
    Optional<Favorite> findByUserIdAndStockCode(String userId, String stockCode);

    //특정 사용자의 즐겨찾기 삭제
    void deleteByUserIdAndStockCode(String userId, String stockCode);
}
