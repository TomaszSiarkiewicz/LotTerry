package pl.lotto;

import pl.lotto.resultchecker.PlayerResultDto;

public record AnnouncerResponseDto(
        String message,
        PlayerResultDto resultDto
) {
}
