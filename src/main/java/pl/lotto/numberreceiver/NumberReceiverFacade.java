package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NumberReceiverFacade {

    private final String CORRECT_RESULT_MESSAGE = "correct";
    private final String FAILED_RESULT_MESSAGE = "failed";
    private final int MAX_NUMBERS_FROM_USER = 6;

    private final NextDrawDateCalculator drawDateCalculator;

    NumberReceiverFacade(NextDrawDateCalculator drawDateCalculator) {
        this.drawDateCalculator = drawDateCalculator;
    }

    NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        if (isSixNumbersFromUser(numbersFromUser)) {
            LocalDateTime nextSaturday = drawDateCalculator.calculateNextDrawDate();
            return new NumberReceiverResultDto(CORRECT_RESULT_MESSAGE, UUID.randomUUID().toString(), nextSaturday);
        } else {
            return new NumberReceiverResultDto(FAILED_RESULT_MESSAGE, null, LocalDateTime.now());
        }
    }

    private boolean isSixNumbersFromUser(List<Integer> numbersFromUser) {
        return numbersFromUser.size() == MAX_NUMBERS_FROM_USER;
    }


}
