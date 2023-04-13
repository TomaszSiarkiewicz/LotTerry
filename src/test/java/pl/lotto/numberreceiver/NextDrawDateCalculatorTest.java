package pl.lotto.numberreceiver;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class NextDrawDateCalculatorTest {
    @Test
    public void should_return_correct_draw_date_for_saturday_at_10() {
        //given
        LocalDateTime before12 = LocalDateTime.of(2023, 4, 8, 10, 0, 0);
        Clock clock = Clock.fixed(before12.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NextDrawDateCalculator dateCalculator = new NextDrawDateCalculator(clock);
        //when
        LocalDateTime before12Result = dateCalculator.calculateNextDrawDate();

        //then
        LocalDateTime thisSaturday = LocalDateTime.of(2023, 4, 8, 12, 0);
        assertThat(before12Result).isEqualTo(thisSaturday);

    }

    @Test
    public void should_return_correct_draw_date_for_saturday_at_12() {
        //given
        LocalDateTime at12 = LocalDateTime.of(2023, 4, 8, 12, 0);

        //when
        Clock clock = Clock.fixed(at12.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NextDrawDateCalculator dateCalculator = new NextDrawDateCalculator(clock);
        LocalDateTime at12Result = dateCalculator.calculateNextDrawDate();

        //then
        LocalDateTime nextSaturday = LocalDateTime.of(2023, 4, 15, 12, 0);
        assertThat(at12Result).isEqualTo(nextSaturday);
    }

    @Test
    public void should_return_correct_draw_date_for_saturday_at_13() {
        //given
        LocalDateTime after12 = LocalDateTime.of(2023, 4, 8, 13, 0);

        //when
        Clock clock = Clock.fixed(after12.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NextDrawDateCalculator dateCalculator = new NextDrawDateCalculator(clock);
        LocalDateTime after12Result = dateCalculator.calculateNextDrawDate();

        //then
        LocalDateTime nextSaturday = LocalDateTime.of(2023, 4, 15, 12, 0);
        assertThat(after12Result).isEqualTo(nextSaturday);
    }


}