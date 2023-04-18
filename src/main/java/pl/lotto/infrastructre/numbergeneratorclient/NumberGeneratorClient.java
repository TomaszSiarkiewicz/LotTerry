package pl.lotto.infrastructre.numbergeneratorclient;


import java.time.LocalDateTime;

public interface NumberGeneratorClient {
    DrawingResultDto retrieveNumbersByDate(LocalDateTime date);
}
