package toyproject.simulated_stock.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import toyproject.simulated_stock.api.auth.handler.CustomAccessDeniedHandler;
import toyproject.simulated_stock.api.auth.handler.CustomAuthenticationEntryPoint;
import toyproject.simulated_stock.api.auth.handler.OAuth2FailureHandler;
import toyproject.simulated_stock.api.auth.handler.OAuth2SuccessHandler;
import toyproject.simulated_stock.api.auth.jwt.TokenAuthenticationFilter;
import toyproject.simulated_stock.api.auth.jwt.TokenExceptionFilter;
import toyproject.simulated_stock.api.auth.service.CustomOAuth2UserService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){ // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                //error endpoint 열기, favicon.ico 401에러 잡기
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/login")  // 로그인 페이지 설정
                        .defaultSuccessUrl("/", true)  // 로그인 성공 후 리다이렉션
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "DELETE"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                )
                .headers(c->c.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .sessionManagement(c->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                        new AntPathRequestMatcher("/"),
                                        new AntPathRequestMatcher("/layout/**"),
                                        new AntPathRequestMatcher("/main"),
                                        new AntPathRequestMatcher("/login"),
                                        new AntPathRequestMatcher("/auth/success"),
                                        new AntPathRequestMatcher("/oauth2/**"),
                                        //API 추가
                                        new AntPathRequestMatcher("/api/**"),  // 주식 리스트 API
                                        new AntPathRequestMatcher("/stock/**"),  // 뷰 로딩 임시
                                        new AntPathRequestMatcher("/stock-view"),  // 뷰 로딩 임시
                                        new AntPathRequestMatcher("/members/**"),  // 임시
                                        new AntPathRequestMatcher("/favorite"),  // 임시
                                        new AntPathRequestMatcher("/search/**")  // 임시
                                ).permitAll()
                                .requestMatchers("/mypage/**").authenticated()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth-> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(new OAuth2FailureHandler())
                )
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 인증되지 않은 사용자의 보호된 리소스 접근 처리
                            response.sendRedirect("/login");
                        })
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();

    }

}
