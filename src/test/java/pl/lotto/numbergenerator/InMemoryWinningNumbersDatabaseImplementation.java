package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryWinningNumbersDatabaseImplementation implements WinningNumbersRepository {
    Map<LocalDate, WinningNumbers> drawingResultDatabase = new HashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        if (drawingResultDatabase.containsKey(winningNumbers.date())) {
            return drawingResultDatabase.get(winningNumbers.date());
        }
        drawingResultDatabase.put(winningNumbers.date(), winningNumbers);
        return drawingResultDatabase.get(winningNumbers.date());
    }

    @Override
    public Optional<WinningNumbers> findByDate(LocalDate date) {
        return Optional.of(drawingResultDatabase.get(date));
    }
}
