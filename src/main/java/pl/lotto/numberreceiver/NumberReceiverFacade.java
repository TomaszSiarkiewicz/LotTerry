package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class NumberReceiverFacade {

    private final String CORRECT_RESULT_MESSAGE = "correct";
    private final String FAILED_RESULT_MESSAGE = "failed";

    private final NextDrawDateCalculator drawDateCalculator;
    private final NumberValidator numberValidator;
    private final LotteryIdGenerable lotteryIdGenerator;

    NumberReceiverFacade(NextDrawDateCalculator drawDateCalculator) {
        this.drawDateCalculator = drawDateCalculator;
        this.numberValidator = new NumberValidator();
    }

    NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        if (numberValidator.validate(numbersFromUser)) {
            LocalDateTime nextSaturday = drawDateCalculator.calculateNextDrawDate();
            return new NumberReceiverResultDto(CORRECT_RESULT_MESSAGE, UUID.randomUUID().toString(), nextSaturday);
        } else {
            return new NumberReceiverResultDto(FAILED_RESULT_MESSAGE, null, LocalDateTime.now());
        }
    }

}
