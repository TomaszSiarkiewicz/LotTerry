package pl.lotto.numberreceiver;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {

    NumberReceiverFacade createForTests(Clock clock, LotteryIdGenerable lotteryIdGenerator) {
        NextDrawDateCalculator nextDrawDateCalculator = new NextDrawDateCalculator(clock);
        return new NumberReceiverFacade(nextDrawDateCalculator);
    }
}
