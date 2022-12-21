package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberReceiverFacadeTest {

    @Test
    public void should_return_correct_result_when_user_gave_six_correct_numbers() {
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC());
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        assert result.message().equals("correct");
    }

    @Test
    public void should_return_failed_result_when_user_gave_less_than_six_numbers() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC());
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assert result.message().equals("failed");
    }

    @Test
    public void should_return_failed_result_when_user_gave_more_than_six_numbers() {
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC());
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);

        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        assertThat(result.message()).isEqualTo("failed");
    }

//    @Test
//    public void should_return_failed_result_when_user_gave_at_least_one_duplicate() {
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 5);
//
//        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
//
//        assert result.message().equals("failed");
//    }

//    @Test
//    public void should_return_failed_result_when_user_gave_number_out_of_bound() {
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 0);
//
//        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
//
//        assert result.message().equals("failed");
//    }

    @Test
    public void should_return_next_saturday_as_draw_date_when_user_played_on_friday() {
        // given
        LocalDateTime date = LocalDateTime.of(2022, 12, 28, 11, 23, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        assertThat(result.drawDate()).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_cos_tam_as_draw_date_when_user_played_on_saturday_at_12_00() {
        // given
        LocalDateTime date = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock);
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
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC());
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.lotteryId()).isNotNull();
        assertThat(result.lotteryId().length()).isEqualTo(36);
//        assertThat(result.lotteryId()).isEqualTo("- - - -");
    }

    @Test
    public void should_return_null_lottery_id_when_something_went_wrong() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC());
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.lotteryId()).isNull();
    }
}
