package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberReceiverFacadeTest {

    LotteryIdGenerable lotteryIdGenerable = new LotteryIdGeneratorTestImpl("defaultId");

    @Test
    public void should_return_correct_result_when_user_gave_six_numbers_in_range_of_1_and_99() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        //when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("correct");
    }

    @Test
    public void should_return_failed_result_when_user_gave_less_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("you have less than six numbers");
    }

    @Test
    public void should_return_failed_result_when_user_gave_more_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);

        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    public void should_return_failed_result_when_user_gave_duplicated_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 5);

        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    public void should_return_failed_result_when_user_gave_number_out_of_bound_1_99() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 0);

        //when
        TicketDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("failed");
    }

    @Test
    public void should_return_next_saturday_as_draw_date_when_user_played_on_friday() {
        // given
        LocalDateTime date = LocalDateTime.of(2023, 1, 11, 11, 23, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2023, 1, 14, 12, 0, 0);
        assertThat(result.drawDate()).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_next_saturday_as_draw_date_when_user_played_on_saturday_at_12_00() {
        // given
        LocalDateTime date = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2023, 1, 7, 12, 0, 0);
        assertThat(result.drawDate()).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_lottery_id_when_all_good() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.lotteryId()).isNotNull();
        assertThat(result.lotteryId().length()).isEqualTo(36);
        assertThat(result.lotteryId()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    @Test
    public void should_return_null_lottery_id_when_something_went_wrong() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.lotteryId()).isNull();
    }
}
