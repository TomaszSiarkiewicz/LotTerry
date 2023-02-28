package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryWinningNumbersDatabaseImplementation implements WinningNumbersRepository {
    Map<LocalDateTime, WinningNumbers> drawingResultDatabase = new HashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        if (drawingResultDatabase.containsKey(winningNumbers.date())) {
            return drawingResultDatabase.get(winningNumbers.date());
        }
        drawingResultDatabase.put(winningNumbers.date(), winningNumbers);
        return drawingResultDatabase.get(winningNumbers.date());
    }

    @Override
    public Optional<WinningNumbers> findByDate(LocalDateTime date) {
        return Optional.of(drawingResultDatabase.get(date));
    }
}
