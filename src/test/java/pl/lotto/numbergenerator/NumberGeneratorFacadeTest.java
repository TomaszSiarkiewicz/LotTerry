package pl.lotto.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.AdjustableClock;
import pl.lotto.numberreceiver.InMemoryTicketDtoDatabaseRepositoryImplementation;
import pl.lotto.numberreceiver.LotteryIdGeneratorTestImpl;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.NumberReceiverFacadeConfiguration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NumberGeneratorFacadeTest {


    @Test
    public void should_return_six_distinct_number_in_range_of_1_99() {
        //given
        LocalDateTime drawingDate = LocalDateTime.of(2023, 3, 22, 12, 0);
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        when(numberReceiverFacade.getNextDrawingDate()).thenReturn(drawingDate);
        WinningNumbersRepository winningNumbersRepository = new InMemoryWinningNumbersDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(winningNumbersRepository, numberReceiverFacade);
        //when
        DrawingResultDto drawingResult = numberGeneratorFacade.generateNumbersAndSave();
        //then
        assertThat(new HashSet<>(drawingResult.numbers())).hasSize(6);
        assertThat(drawingResult.numbers()).noneMatch(integer -> integer > 99 || integer < 0);
    }

    @Test
    public void should_save_numbers_for_current_draw_date() {
        //given
        LocalDateTime drawingDate = LocalDateTime.of(2023, 3, 22, 12, 0);
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        when(numberReceiverFacade.getNextDrawingDate()).thenReturn(drawingDate);
        WinningNumbersRepository winningNumbersRepository = new InMemoryWinningNumbersDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(winningNumbersRepository, numberReceiverFacade);
        //when
        DrawingResultDto drawingResult = numberGeneratorFacade.generateNumbersAndSave();
        //then
        DrawingResultDto resultDto = numberGeneratorFacade.retrieveNumbersByDate(drawingDate);

        assertThat(drawingResult).isEqualTo(resultDto);
    }

    @Test
    public void should_return_null_saving_drawing_result_when_drawn_number_in_database_for_given_day() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023, 1, 1, 12, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, new LotteryIdGeneratorTestImpl("defaultId"), new InMemoryTicketDtoDatabaseRepositoryImplementation());
        WinningNumbersRepository winningNumbersRepository = new InMemoryWinningNumbersDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(winningNumbersRepository, numberReceiverFacade);
        //when
        DrawingResultDto firstDrawingResult = numberGeneratorFacade.generateNumbersAndSave();
        DrawingResultDto secondDrawingSameDay = numberGeneratorFacade.generateNumbersAndSave();
        //then
        assertThat(numberGeneratorFacade.retrieveNumbersByDate(numberReceiverFacade.getNextDrawingDate()).numbers()).isEqualTo(firstDrawingResult.numbers());
    }

    @Test
    public void should_return_one_drawing_result_for_given_saturday() {
        //given
        AdjustableClock clock = new AdjustableClock(LocalDateTime.of(2023, 1, 1, 12, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, new LotteryIdGeneratorTestImpl("defaultId"), new InMemoryTicketDtoDatabaseRepositoryImplementation());
        WinningNumbersRepository winningNumbersRepository = new InMemoryWinningNumbersDatabaseImplementation();
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorFacadeConfiguration().createForTests(winningNumbersRepository, numberReceiverFacade);
        //when
        LocalDateTime firstDrawingDate = numberReceiverFacade.getNextDrawingDate();
        DrawingResultDto twoWeeksBackDrawingResult = numberGeneratorFacade.generateNumbersAndSave();
        clock.plusDays(7);
        LocalDateTime secondDrawingDate = numberReceiverFacade.getNextDrawingDate();
        DrawingResultDto oneWeeksBackDrawingResult = numberGeneratorFacade.generateNumbersAndSave();
        //then
        assertThat(oneWeeksBackDrawingResult).isEqualTo(numberGeneratorFacade.retrieveNumbersByDate(secondDrawingDate));
        assertThat(twoWeeksBackDrawingResult).isEqualTo(numberGeneratorFacade.retrieveNumbersByDate(firstDrawingDate));
    }
}