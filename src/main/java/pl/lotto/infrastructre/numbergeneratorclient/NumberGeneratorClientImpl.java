package pl.lotto.infrastructre.numbergeneratorclient;

import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NumberGeneratorClientImpl implements NumberGeneratorClient {
    private final WebClient webClient;

    public NumberGeneratorClientImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public DrawingResultDto retrieveNumbersByDate(LocalDateTime date) {
        String dateString = date.format(DateTimeFormatter.ISO_DATE_TIME).strip();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/winnum/{date}")
                        .build(dateString))
                .retrieve()
                .bodyToMono(DrawingResultDto.class)
                .block();
    }
}
