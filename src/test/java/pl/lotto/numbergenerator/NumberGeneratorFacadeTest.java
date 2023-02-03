package pl.lotto.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.AdjustableClock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberGeneratorFacadeTest {


    @Test
    public void should_return_six_distinct_number_in_range_of_1_99() {
        //given
        DrawingResultDtoRepository drawingResultDtoRepository = new InMemoryDrawingResultDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(drawingResultDtoRepository);
        //when
        DrawingResultDto drawingResult = numberGeneratorFacade.generateNumbersAndSave(LocalDate.now());
        //then
        drawingResult.numbers().forEach(number -> assertTrue(number >= 1 && number <= 99));
        assertThat(new HashSet<>(drawingResult.numbers()).size()).isEqualTo(6);
    }

    @Test
    public void should_save_numbers_for_current_draw_date() {
        //given
        DrawingResultDtoRepository drawingResultDtoRepository = new InMemoryDrawingResultDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(drawingResultDtoRepository);
        //when
        DrawingResultDto drawingResult = numberGeneratorFacade.generateNumbersAndSave(LocalDate.now());
        //then
        DrawingResultDto resultDto = drawingResultDtoRepository.getByDate(LocalDate.now());
        assertThat(drawingResult).isEqualTo(resultDto);
    }

    @Test
    public void should_return_null_saving_drawing_result_when_drawn_number_in_database_for_given_day() {
        //given
        DrawingResultDtoRepository drawingResultDtoRepository = new InMemoryDrawingResultDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(drawingResultDtoRepository);
        //when
        DrawingResultDto firstDrawingResult = numberGeneratorFacade.generateNumbersAndSave(LocalDate.now());
        DrawingResultDto secondDrawingSameDay = numberGeneratorFacade.generateNumbersAndSave(LocalDate.now());
        //then
        assertThat(secondDrawingSameDay).isEqualTo(null);
    }

    @Test
    public void should_return_one_drawing_result_for_last_saturday() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.now().toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        DrawingResultDtoRepository drawingResultDtoRepository = new InMemoryDrawingResultDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(drawingResultDtoRepository);
        //when
        clock.minusDays(14);
        DrawingResultDto twoWeeksBackDrawingResult = numberGeneratorFacade.generateNumbersAndSave(clock.getLocalDate());
        clock.plusDays(7);
        DrawingResultDto oneWeeksBackDrawingResult = numberGeneratorFacade.generateNumbersAndSave(clock.getLocalDate());
        //then
        assertThat(oneWeeksBackDrawingResult).isEqualTo(numberGeneratorFacade.retrieveNumbersByDate(clock.getLocalDate()));
    }
}