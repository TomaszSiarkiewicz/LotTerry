package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.List;

public record DrawingResultDto(
        LocalDate date,
        List<Integer> numbers
) {
}
