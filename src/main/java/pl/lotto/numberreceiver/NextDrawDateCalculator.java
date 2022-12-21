package pl.lotto.numberreceiver;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

class NextDrawDateCalculator {

    private final Clock clock;

    NextDrawDateCalculator(Clock clock) {
        this.clock = clock;
    }

    LocalDateTime calculateNextDrawDate() {
        LocalDateTime now = LocalDateTime.now(clock);
        return now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(12)
                .withMinute(0);
    }
}
