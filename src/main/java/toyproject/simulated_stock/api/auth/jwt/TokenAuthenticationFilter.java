package toyproject.simulated_stock.api.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import toyproject.simulated_stock.api.common.constants.TokenKey;
import toyproject.simulated_stock.api.common.utils.TokenCookieManager;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final TokenCookieManager tokenCookieManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = resolveToken(request);

            if (StringUtils.hasText(accessToken)) {
                if (tokenProvider.validateToken(accessToken)) {
                    setAuthentication(accessToken);
                } else {
                    String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken);

                    if (StringUtils.hasText(reissueAccessToken)) {
                        setAuthentication(reissueAccessToken);
                        tokenCookieManager.addAccessTokenToCookie(response, reissueAccessToken);
                    }
                }
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            tokenCookieManager.removeTokenCookie(response);
        }
            filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기

        if (ObjectUtils.isEmpty(token) || !token.startsWith(TokenKey.TOKEN_PREFIX)) {
            if(cookies == null){
                return null;
            }
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기

                String value = c.getValue(); // 쿠키 값 가져오기
                if (name.equals(AUTHORIZATION)) {
                    token = value;
                }
            }

            return token;
        }

        return token.substring(TokenKey.TOKEN_PREFIX.length());
    }
}
