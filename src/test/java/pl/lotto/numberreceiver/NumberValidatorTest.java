package pl.lotto.numberreceiver;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NumberValidatorTest {

    @Test
    public void should_return_false_when_user_gave_less_than_six_numbers() {
        // given
        NumberValidator numberValidator = new NumberValidator();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        // when
        boolean result = numberValidator.validate(numbersFromUser);

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_false_when_user_gave_more_than_six_numbers() {
        //given
        NumberValidator numberValidator = new NumberValidator();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);

        //when
        boolean result = numberValidator.validate(numbersFromUser);

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_failed_result_when_user_gave_at_least_one_duplicate() {
        //given
        NumberValidator numberValidator = new NumberValidator();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 5);

        //when
        boolean result = numberValidator.validate(numbersFromUser);

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void should_return_failed_result_when_user_gave_number_out_of_bound() {
        //given
        NumberValidator numberValidator = new NumberValidator();
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 0);

        //when
        boolean result = numberValidator.validate(numbersFromUser);

        //then
        assertThat(result).isEqualTo(false);
    }
}