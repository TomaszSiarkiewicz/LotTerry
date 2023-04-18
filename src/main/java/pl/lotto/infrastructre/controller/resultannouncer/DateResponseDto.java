package pl.lotto.infrastructre.controller.resultannouncer;

import java.time.LocalDateTime;

public record DateResponseDto(
        LocalDateTime drawDate
) {
}
