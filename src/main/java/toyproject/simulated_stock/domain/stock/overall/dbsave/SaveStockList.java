package toyproject.simulated_stock.domain.stock.overall.dbsave;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import toyproject.simulated_stock.domain.stock.overall.config.DateConfig;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;
import toyproject.simulated_stock.domain.stock.overall.repository.StockListRepository;
import toyproject.simulated_stock.global.config.OpenApiSecretInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveStockList {
    private final OpenApiSecretInfo openApiSecretInfo;

    private final DateConfig dateConfig;

    private final StockListRepository stockListRepository;

    private final String STOCK_DEFAULT_URL = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService";

    private final RestTemplate restTemplate;

    @PostConstruct
    @Scheduled(cron = "0 1 11 * * *", zone = "Asia/Seoul")
    public void saveStockList(){
        String url = STOCK_DEFAULT_URL + "/getStockPriceInfo";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", openApiSecretInfo.getServiceKey())
                .queryParam("numOfRows",2000)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .queryParam("beginBasDt", dateConfig.getFromFiveDaysAgoToNow())
                .build();

        ResponseEntity<String> responseData = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        String responseDataBody = responseData.getBody();

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(responseDataBody);
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray item = (JSONArray) items.get("item");

            for(int i=0; i<item.size(); i++){
                JSONObject tmp = (JSONObject) item.get(i);
                StockList infoObj = new StockList(
                        i+(long) 1,
                        (String) tmp.get("basDt"),
                        (String) tmp.get("srtnCd"),
                        (String) tmp.get("isinCd"),
                        (String) tmp.get("itmsNm"),
                        (String) tmp.get("mrktCtg"),
                        (String) tmp.get("clpr"),
                        (String) tmp.get("vs"),
                        (String) tmp.get("fltRt"),
                        (String) tmp.get("mkp"),
                        (String) tmp.get("hipr"),
                        (String) tmp.get("lopr"),
                        (String) tmp.get("trqu"),
                        (String) tmp.get("trPrc"),
                        (String) tmp.get("lstgStCnt"),
                        (String) tmp.get("mrktTotAmt")
                );
                stockListRepository.save(infoObj);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 매일 오전 11시에 DB 주식정보 삭제
    @Scheduled(cron = "0 0 11 * * *", zone = "Asia/Seoul")
    public void deleteStockList() {
        stockListRepository.deleteAll();
    }

}
