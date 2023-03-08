package pl.lotto.infrastructre.controller.resultannouncer;

import pl.lotto.resultchecker.PlayerResultDto;

public record AnnouncerResponseDto(
        String message,
        PlayerResultDto resultDto
) {
}
