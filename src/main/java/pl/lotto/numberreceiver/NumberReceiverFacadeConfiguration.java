package pl.lotto.numberreceiver;

import java.time.Clock;

public class NumberReceiverFacadeConfiguration {
    NumberReceiverFacade createForTests(Clock clock) {
        NextDrawDateCalculator nextDrawDateCalculator = new NextDrawDateCalculator(clock);
        return new NumberReceiverFacade(nextDrawDateCalculator);
    }
}
