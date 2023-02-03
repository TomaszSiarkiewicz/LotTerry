package pl.lotto.numbergenerator;

import java.time.LocalDate;

public interface DrawingResultDtoRepository {
    DrawingResultDto save(DrawingResultDto drawingResultDto);

    DrawingResultDto getByDate(LocalDate now);
}
