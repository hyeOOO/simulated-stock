package toyproject.simulated_stock.domain.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import toyproject.simulated_stock.domain.redis.entity.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findByAccessToken(String accessToken);
}
