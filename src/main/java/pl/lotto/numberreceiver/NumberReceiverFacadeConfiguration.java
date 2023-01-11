package pl.lotto.numberreceiver;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests(Clock clock, LotteryIdGenerable lotteryIdGenerator) {
        NextDrawDateCalculator nextDrawDateCalculator = new NextDrawDateCalculator(clock);
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(nextDrawDateCalculator, numberValidator, lotteryIdGenerator);
    }
}
