package toyproject.simulated_stock.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    //RestTemplate Pool 만들어두기
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}