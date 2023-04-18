package pl.lotto.infrastructre.numbergeneratorclient;

import java.time.LocalDateTime;
import java.util.List;

public record DrawingResultDto(
        LocalDateTime date,
        List<Integer> numbers
) {
}
