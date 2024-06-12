package toyproject.simulated_stock.domain.stock.detail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationDto;
import toyproject.simulated_stock.domain.stock.detail.token.AccessTokenService;
import toyproject.simulated_stock.global.config.OpenApiSecretInfo;
import toyproject.simulated_stock.global.exception.BusinessLogicException;
import toyproject.simulated_stock.global.exception.ExceptionCode;

@RequiredArgsConstructor
@Service
public class DetailStockService {
    private final OpenApiSecretInfo openApiSecretInfo;

    private final String STOCK_DEFAULT_URL = "https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/quotations";

    private final RestTemplate restTemplate;

    private HttpHeaders httpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + AccessTokenService.accessToken);
        headers.set("appkey", openApiSecretInfo.getAppKey());
        headers.set("appsecret", openApiSecretInfo.getAppSecret());
        return headers;
    }

    @Transactional(readOnly = true)
    public StockQuotationDto getQuotations(String stockCode){
        HttpHeaders requestHeaders = httpHeaders();
        requestHeaders.set("tr_id", "FHKST01010100"); //주식현재가 시세 거래ID
        HttpEntity<String> requestMessage = new HttpEntity<>(requestHeaders);

        String url = STOCK_DEFAULT_URL + "/inquire-price";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE","J")
                .queryParam("FID_INPUT_ISCD",stockCode)
                .build();

        ResponseEntity<StockQuotationDto> response;

        try{
            response = restTemplate.exchange(
                    uriBuilder.toString(),
                    HttpMethod.GET,
                    requestMessage,
                    StockQuotationDto.class
            );

            return response.getBody();

        }catch (Exception e){
            handleErrors(e);
        }
        return null;
    }



    private void handleErrors(Exception e){
        String[] errMsg = e.getMessage().split("\"");
        String errCode = errMsg[8];

        switch (errCode){
            case "EGW00001":    // 일시적인 오류
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            case "EGW00002":    // 서버 에러
                throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
            case "EGW00121":    // 유효하지 않은 토큰
                throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
            case "EGW00201":    // 초당 거래건수 초과
                throw new BusinessLogicException(ExceptionCode.UNABLE_TO_REQUEST_AGAIN);
        }
    }
}
