package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WinningNumbersRepository {
    WinningNumbers save(WinningNumbers winningNumbers);

    Optional<WinningNumbers> findByDate(LocalDateTime now);
}
