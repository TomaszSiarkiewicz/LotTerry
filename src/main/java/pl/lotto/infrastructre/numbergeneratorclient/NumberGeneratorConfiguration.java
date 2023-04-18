package pl.lotto.infrastructre.numbergeneratorclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NumberGeneratorConfiguration {
    @Value("${lotto.lotto-number-generator.baseURL}")
    private String BASE_URL;

    @Bean
    public NumberGeneratorClientImpl numberGeneratorClientImpl(){
        WebClient webClient = WebClient
                .builder()
                .baseUrl(BASE_URL)
                .build();
        return new NumberGeneratorClientImpl(webClient);
    }
}
