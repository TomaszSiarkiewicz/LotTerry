package pl.lotto.numberreceiver;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

class NextDrawDateCalculator {

    private final Clock clock;

    NextDrawDateCalculator(Clock clock) {
        this.clock = clock;
    }

    LocalDateTime calculateNextDrawDate() {
        ZonedDateTime now = ZonedDateTime.now(clock);
        if (isSaturdayBefore12(now)) {
            return now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                    .withHour(12)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0)
                    .toLocalDateTime()
                    ;

        }
        return now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(12)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .toLocalDateTime()
                ;
    }

    private boolean isSaturdayBefore12(ZonedDateTime now) {
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(now.toLocalDateTime());
        int offsetHours = (offset.get(ChronoField.OFFSET_SECONDS))/60/60;

        return now.getDayOfWeek().equals(DayOfWeek.SATURDAY) && now.getHour()- offsetHours < 12;
    }
}
