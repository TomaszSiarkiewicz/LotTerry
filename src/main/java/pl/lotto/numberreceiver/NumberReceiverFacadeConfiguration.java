package pl.lotto.numberreceiver;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {

    public NumberReceiverFacade createForTests(Clock clock, LotteryIdGenerable lotteryIdGenerator, TicketDtoRepository repository) {
        NextDrawDateCalculator nextDrawDateCalculator = new NextDrawDateCalculator(clock);
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(nextDrawDateCalculator, numberValidator, lotteryIdGenerator, repository);
    }
}
