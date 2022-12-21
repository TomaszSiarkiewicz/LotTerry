package pl.lotto.numberreceiver;

import java.util.List;

public class NumberReceiverFacade {

    final String CORRECT_RESULT_MESSAGE = "correct";
    final String FAILED_RESULT_MESSAGE = "failed";
    final int MAX_NUMBERS_FROM_USER = 6;

    NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        if (isSixNumbersFromUser(numbersFromUser)) {
            return new NumberReceiverResultDto(CORRECT_RESULT_MESSAGE);
        } else {
            return new NumberReceiverResultDto(FAILED_RESULT_MESSAGE);
        }
    }

    private boolean isSixNumbersFromUser(List<Integer> numbersFromUser) {
        return numbersFromUser.size() == MAX_NUMBERS_FROM_USER;
    }
}
