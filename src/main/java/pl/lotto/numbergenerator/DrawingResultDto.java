package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.Set;

public record DrawingResultDto(
        LocalDate date,
        Set<Integer> numbers
) {
}
