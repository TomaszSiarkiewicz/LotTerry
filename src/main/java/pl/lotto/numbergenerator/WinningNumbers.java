package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.List;

public record WinningNumbers(
        LocalDateTime date,
        List<Integer> numbers
) {
}
