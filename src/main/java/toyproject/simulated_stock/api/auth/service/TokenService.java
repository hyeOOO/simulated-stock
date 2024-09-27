package toyproject.simulated_stock.api.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.simulated_stock.api.auth.exception.TokenException;
import toyproject.simulated_stock.domain.redis.entity.Token;
import toyproject.simulated_stock.domain.redis.repository.TokenRepository;

import static toyproject.simulated_stock.api.exception.ErrorCode.TOKEN_EXPIRED;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public void deleteRefreshToken(String memberKey) {
        tokenRepository.deleteById(memberKey);
    }

    @Transactional
    public void saveOrUpdate(String memberKey, String refreshToken, String accessToken) {
        // 액세스 토큰을 찾아서
        Token token = tokenRepository.findByAccessToken(accessToken) // 액세스 토큰을 기준으로 tokenRepository에 있는 Token 객체 찾기
                //만약 데이터베이스에서 해당 액세스 토큰에 해당하는 Token 객체가 존재할 경우, 해당 객체(o)에 대해 updateRefreshToken(refreshToken) 메서드를 호출
                //=>존재하는 토큰이 있다면 그 토큰의 리프레시 토큰 값을 새로 받은 refreshToken으로 업데이트
                .map(o -> o.updateRefreshToken(refreshToken))
                // 만약 주어진 액세스 토큰으로 토큰을 찾지 못했다면, orElseGet을 통해 새로운 Token 객체를 생성
                .orElseGet(() -> new Token(memberKey, refreshToken, accessToken));

        tokenRepository.save(token);
    }

    public Token findByAccessTokenOrThrow(String accessToken) {
        return tokenRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new TokenException(TOKEN_EXPIRED));
    }

    @Transactional
    public void updateToken(String accessToken, Token token) {
        token.updateAccessToken(accessToken);
        tokenRepository.save(token);
    }
}
