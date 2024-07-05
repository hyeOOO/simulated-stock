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
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;
import toyproject.simulated_stock.domain.stock.overall.repository.StockIndexRepository;
import toyproject.simulated_stock.global.config.OpenApiSecretInfo;

@Slf4j
@RequiredArgsConstructor
@Service
public class SavaStockIndex {
    private final OpenApiSecretInfo openApiSecretInfo;

    private final DateConfig dateConfig;

    private final String STOCK_INDEX_URL = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService";

    private final RestTemplate restTemplate;

    private final StockIndexRepository stockIndexRepository;

    @PostConstruct
    @Scheduled(cron = "15 5 11 * * *", zone = "Asia/Seoul")
    public void saveKOSPIStockIndex(){
        String url = STOCK_INDEX_URL + "/getStockMarketIndex";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", openApiSecretInfo.getServiceKey())
                .queryParam("numOfRows", 5)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .queryParam("beginBasDt", dateConfig.getFiveDaysFromToday())
                .queryParam("idxNm", "코스피")
                .build();

        ResponseEntity<String> responseData = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        String responseDataBody = responseData.getBody();

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseDataBody);

            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");

            JSONArray item = (JSONArray) items.get("item");

            for(int i=0; i<item.size(); i++){
                JSONObject tmp = (JSONObject) item.get(i);
                StockIndex obj = new StockIndex(
                        i+(long)1,
                        (String) tmp.get("lsYrEdVsFltRt"),
                        (String) tmp.get("basPntm"),
                        (String) tmp.get("basIdx"),
                        (String) tmp.get("basDt"),
                        (String) tmp.get("idxCsf"),
                        (String) tmp.get("idxNm"),
                        (String) tmp.get("epyItmsCnt"),
                        (String) tmp.get("clpr"),
                        (String) tmp.get("vs"),
                        (String) tmp.get("fltRt"),
                        (String) tmp.get("mkp"),
                        (String) tmp.get("hipr"),
                        (String) tmp.get("lopr"),
                        (String) tmp.get("trqu"),
                        (String) tmp.get("trPrc"),
                        (String) tmp.get("lstgMrktTotAmt"),
                        (String) tmp.get("lsYrEdVsFltRg"),
                        (String) tmp.get("yrWRcrdHgst"),
                        (String) tmp.get("yrWRcrdHgstDt"),
                        (String) tmp.get("yrWRcrdLwst"),
                        (String) tmp.get("yrWRcrdLwstDt")
                );

                stockIndexRepository.save(obj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 1 11 * * *", zone = "Asia/Seoul")
    public void saveKOSDAQStockIndex(){
        String url = STOCK_INDEX_URL + "/getStockMarketIndex";

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", openApiSecretInfo.getServiceKey())
                .queryParam("numOfRows", 1000)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .queryParam("beginBasDt", dateConfig.getFiveDaysFromToday())
                .build();

        ResponseEntity<String> responseData = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);

        String responseDataBody = responseData.getBody();

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseDataBody);

            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");

            JSONArray item = (JSONArray) items.get("item");

            for(int i=0; i<item.size(); i++){
                JSONObject tmp = (JSONObject) item.get(i);
                StockIndex obj = new StockIndex(
                        i+(long)1,
                        (String) tmp.get("lsYrEdVsFltRt"),
                        (String) tmp.get("basPntm"),
                        (String) tmp.get("basIdx"),
                        (String) tmp.get("basDt"),
                        (String) tmp.get("idxCsf"),
                        (String) tmp.get("idxNm"),
                        (String) tmp.get("epyItmsCnt"),
                        (String) tmp.get("clpr"),
                        (String) tmp.get("vs"),
                        (String) tmp.get("fltRt"),
                        (String) tmp.get("mkp"),
                        (String) tmp.get("hipr"),
                        (String) tmp.get("lopr"),
                        (String) tmp.get("trqu"),
                        (String) tmp.get("trPrc"),
                        (String) tmp.get("lstgMrktTotAmt"),
                        (String) tmp.get("lsYrEdVsFltRg"),
                        (String) tmp.get("yrWRcrdHgst"),
                        (String) tmp.get("yrWRcrdHgstDt"),
                        (String) tmp.get("yrWRcrdLwst"),
                        (String) tmp.get("yrWRcrdLwstDt")
                );

                stockIndexRepository.save(obj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 11 * * *", zone = "Asia/Seoul")
    public void deleteStockIndex() {
        stockIndexRepository.deleteAll();
    }
}
