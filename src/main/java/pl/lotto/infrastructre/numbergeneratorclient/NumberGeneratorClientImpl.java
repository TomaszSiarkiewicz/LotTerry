package pl.lotto.infrastructre.numbergeneratorclient;

import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class NumberGeneratorClientImpl implements NumberGeneratorClient {
    private final WebClient webClient;

    public NumberGeneratorClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public DrawingResultDto retrieveNumbersByDate(LocalDateTime date) {
        String dateString = date.toInstant(ZoneOffset.UTC).toEpochMilli() + "";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/winnum/{date}")
                        .build(dateString))
                .retrieve()
                .bodyToMono(DrawingResultDto.class)
                .block();
    }
}
