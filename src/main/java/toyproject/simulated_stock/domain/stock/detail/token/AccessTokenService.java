package toyproject.simulated_stock.domain.stock.detail.token;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import toyproject.simulated_stock.global.config.OpenApiSecretInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class AccessTokenService {

    public static String accessToken;

    private final OpenApiSecretInfo openApiSecretInfo;

    /**
     * 매일 자정에 한국투자증권 Access 토큰 부여 받기(유효기간 24시간)
     */
    @PostConstruct
    @Scheduled(cron="0 0 0 * * *", zone = "Asia/Seoul")
    public void getAccessToken() {
        ConcurrentHashMap<String, String> requestBody = new ConcurrentHashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", openApiSecretInfo.getAppKey());
        requestBody.put("appsecret", openApiSecretInfo.getAppSecret());

        HttpEntity<Object> requestMessage = new HttpEntity<>(requestBody);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response =
                restTemplate.exchange(
                    "https://openapivts.koreainvestment.com:29443/oauth2/tokenP",
                        HttpMethod.POST,
                        requestMessage,
                        String.class
                );

        String[] responseBody = response.getBody().split("\""); // 큰따옴표 기준 나누기

        accessToken = responseBody[3];
    }
}
