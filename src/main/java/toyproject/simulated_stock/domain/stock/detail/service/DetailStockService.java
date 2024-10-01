package toyproject.simulated_stock.domain.stock.detail.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import toyproject.simulated_stock.api.config.OpenApiSecretInfo;
import toyproject.simulated_stock.domain.stock.detail.dto.StockBasicInfoDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockInvestorsDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsByPeriodDto;
import toyproject.simulated_stock.domain.stock.detail.dto.StockQuotationsDto;
import toyproject.simulated_stock.domain.stock.detail.option.QuotationsByPeriodOption;
import toyproject.simulated_stock.domain.stock.detail.token.AccessTokenService;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.query.QueryStockListRepository;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.api.exception.ExceptionCode;

@RequiredArgsConstructor
@Service
@Slf4j
public class DetailStockService {
    private final OpenApiSecretInfo openApiSecretInfo;
    @Qualifier("stockListQueryRepositoryImpl") // 명시적으로 주입할 빈을 지정
    private final QueryStockListRepository stockListQueryRepository;

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
    public StockQuotationsDto getQuotations(String stockCode){
        HttpHeaders requestHeaders = httpHeaders();
        requestHeaders.set("tr_id", "FHKST01010100"); //주식현재가 시세 거래ID
        HttpEntity<String> requestMessage = new HttpEntity<>(requestHeaders);

        String url = STOCK_DEFAULT_URL + "/inquire-price";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE","J")
                .queryParam("FID_INPUT_ISCD",stockCode)
                .build();

        ResponseEntity<StockQuotationsDto> response;

        try{
            response = restTemplate.exchange(
                    uriBuilder.toString(),
                    HttpMethod.GET,
                    requestMessage,
                    StockQuotationsDto.class
            );

            return response.getBody();

        }catch (Exception e){
            handleErrors(e);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public StockInvestorsDto getInvestors(String stockCode){
        HttpHeaders requestHeaders = httpHeaders();
        requestHeaders.set("tr_id", "FHKST01010900");//주식현재가 투자자
        HttpEntity<String> requestMessage = new HttpEntity<>(requestHeaders);

        String url = STOCK_DEFAULT_URL + "/inquire-investor";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", stockCode)
                .build();

        ResponseEntity<StockInvestorsDto> response;

        try{
            response = restTemplate.exchange(
                    uriBuilder.toString(),
                    HttpMethod.GET,
                    requestMessage,
                    StockInvestorsDto.class
            );

            return response.getBody();

        }catch(Exception e){
            handleErrors(e);
        }

        return null;
    }

    @Transactional(readOnly = true)
    public StockQuotationsByPeriodDto getQuotationsByPeriod(String stockCode, QuotationsByPeriodOption option){
        HttpHeaders requestHeaders = httpHeaders();
        requestHeaders.set("tr_id", "FHKST03010100");//주식현재가 투자자
        HttpEntity<String> requestMessage = new HttpEntity<>(requestHeaders);

        String url = STOCK_DEFAULT_URL + "/inquire-daily-itemchartprice";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", stockCode)
                .queryParam("FID_INPUT_DATE_1", option.getStartDate())
                .queryParam("FID_INPUT_DATE_2", option.getEndDate())
                .queryParam("FID_PERIOD_DIV_CODE", option.getPeriodCode())
                .queryParam("FID_ORG_ADJ_PRC", option.getOrgAdjPrc())
                .build();

        ResponseEntity<StockQuotationsByPeriodDto> response;

        try{
            response = restTemplate.exchange(
                    uriBuilder.toString(),
                    HttpMethod.GET,
                    requestMessage,
                    StockQuotationsByPeriodDto.class
            );

            return response.getBody();

        }catch(Exception e){
            handleErrors(e);
        }

        return null;
    }

    /**
     * 주어진 종목 코드와 시장 카테고리로 최신 주식 정보를 조회
     * @param stockCode
     * @param marketCategory
     * @return 특정 종목의 정보를 담은 DTO
     */
    @Transactional(readOnly = true)
    public StockBasicInfoDto getStockBasicInfo(String stockCode, String marketCategory) {
        // QueryDSL을 사용하여 최신 주식 정보 조회
        StockList stockInfo = stockListQueryRepository.findLatestStockInfo(stockCode, marketCategory);

        // DTO로 변환 후 반환
        return StockBasicInfoDto.convertEntityToDto(stockInfo);
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
