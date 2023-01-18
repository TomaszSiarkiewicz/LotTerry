package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

class NumberReceiverFacade {

    private final NextDrawDateCalculator drawDateCalculator;
    private final NumberValidator numberValidator;
    private final LotteryIdGenerable lotteryIdGenerator;

    NumberReceiverFacade(NextDrawDateCalculator drawDateCalculator, NumberValidator numberValidator, LotteryIdGenerable lotteryIdGenerator) {
        this.drawDateCalculator = drawDateCalculator;
        this.numberValidator = numberValidator;
        this.lotteryIdGenerator = lotteryIdGenerator;
    }

    TicketDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validationResult = numberValidator.validate(numbersFromUser);
        if (validationResult.isValid()) {
            LocalDateTime nextSaturday = drawDateCalculator.calculateNextDrawDate();
            return new TicketDto(validationResult.message(), UUID.randomUUID().toString(), nextSaturday);
        } else {
            return new TicketDto(validationResult.message(), null, LocalDateTime.now());
        }
    }

    AllTicketsDto retriveAllUserTicketsForNextDrawDate() {
        LocalDateTime nextDrawDate = LocalDateTime.of(2023, 1, 14, 12, 0, 0);
        String lotteryId = lotteryIdGenerator.generateId();
        List<TicketDto> tickets = List.of(new TicketDto("correct", lotteryId, nextDrawDate));
        return new AllTicketsDto(tickets);
    }

}
