package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.Set;

public record WinningNumbers(
        LocalDate date,
        Set<Integer> numbers
) {
}
