package pl.lotto.resultchecker;

import java.time.LocalDateTime;
import java.util.List;

public record PlayerResultDto(
        List<Integer> userNumbers,
        LocalDateTime drawDate,
        boolean isWinner
) {
}
