package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.Optional;

public interface WinningNumbersRepository {
    WinningNumbers save(WinningNumbers winningNumbers);

    Optional<WinningNumbers> findByDate(LocalDate now);
}
