package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InMemoryDrawingResultDatabaseImplementation implements DrawingResultDtoRepository {
    Map<LocalDate, DrawingResultDto> drawingResultDatabase = new HashMap<>();

    @Override
    public DrawingResultDto save(DrawingResultDto drawingResult) {
        if (drawingResultDatabase.put(drawingResult.date(), drawingResult) == null) {
            return drawingResult;
        } else {
            return null;
        }
    }
    @Override
    public DrawingResultDto getByDate(LocalDate date) {
        return drawingResultDatabase.get(date);
    }
}
