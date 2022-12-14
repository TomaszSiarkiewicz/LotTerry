package pl.lotto.numberreceiver;

import java.util.List;
import org.junit.jupiter.api.Test;

public class NumberReceiverFacadeTest {

    @Test
    public void should_return_correct_result_when_user_gave_six_correct_numbers() {
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        assert result.message().equals("correct");
    }

    @Test
    public void should_return_failed_result_when_user_gave_less_than_six_numbers() {
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        assert result.message().equals("failed");
    }

    @Test
    public void should_return_failed_result_when_user_gave_more_than_six_numbers() {

    }

    @Test
    public void should_return_failed_result_when_user_gave_atleast_one_duplicate() {

    }

//    @Test
//    public void should_return_next_saturday_as_draw_date_when_user_played_on_friday() {
//
//    }
}
