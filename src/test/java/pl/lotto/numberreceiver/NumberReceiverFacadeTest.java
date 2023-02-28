package pl.lotto.numberreceiver;

import org.junit.jupiter.api.Test;
import pl.lotto.AdjustableClock;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberReceiverFacadeTest {

    LotteryIdGenerable lotteryIdGenerable = new LotteryIdGeneratorTestImpl("defaultId");
    TicketDtoRepository repository = new InMemoryTicketDtoDatabaseRepositoryImplementation();


    @Test
    public void should_return_correct_result_when_user_gave_six_numbers_in_range_of_1_and_99() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        //when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Success!");
    }

    @Test
    public void should_return_failed_result_when_user_gave_less_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        //when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed! Fix those issues: Not enough numbers");
    }

    @Test
    public void should_return_failed_result_when_user_gave_more_than_six_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);

        //when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed! Fix those issues: Too many numbers");
    }

    @Test
    public void should_return_failed_result_when_user_gave_duplicated_numbers() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 5);

        //when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed! Fix those issues: Numbers are duplicated");
    }

    @Test
    public void should_return_failed_result_when_user_gave_number_out_of_bound_1_99() {
        //given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 0);

        //when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        //then
        assertThat(result.message()).isEqualTo("Failed! Fix those issues: At least one number is incorrect");
    }

    @Test
    public void should_return_next_saturday_as_draw_date_when_user_played_on_friday() {
        // given
        LocalDateTime date = LocalDateTime.of(2023, 1, 11, 11, 23, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2023, 1, 14, 12, 0, 0);
        assertThat(result.ticketDto().drawDate()).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_next_saturday_as_draw_date_when_user_played_on_saturday_at_12_00() {
        // given
        LocalDateTime date = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2023, 1, 7, 12, 0, 0);
        assertThat(result.ticketDto().drawDate()).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_lottery_id_when_all_good() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);

        // when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.ticketDto().lotteryId()).isNotNull();
        assertThat(result.ticketDto().lotteryId().length()).isEqualTo(36);
        assertThat(result.ticketDto().lotteryId()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$");
    }

    @Test
    public void should_return_null_lottery_id_when_something_went_wrong() {
        // given
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(Clock.systemUTC(), lotteryIdGenerable, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);

        // when
        InputNumbersResponse result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        assertThat(result.ticketDto().lotteryId()).isNull();
    }

    @Test
    public void should_return_all_tickets_for_next_draw_date() {
        // given
        LocalDateTime date = LocalDateTime.of(2022, 12, 31, 12, 0, 0);
        Clock clock = Clock.fixed(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        LotteryIdGeneratorTestImpl lotteryIdGeneratorTest = new LotteryIdGeneratorTestImpl("id1");
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGeneratorTest, repository);

        TicketDto ticket = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6)).ticketDto();
        // when
        Optional<List<TicketDto>> allTickets = numberReceiverFacade.retrieveAllUserTicketsForNextDrawDate();

        //then
        assertThat(allTickets.get()).containsOnly(ticket);
    }

    @Test
    public void should_return_one_ticket_for_next_draw_date() {
        //given
        LotteryIdGeneratorTestImpl lotteryIdGeneratorTest = new LotteryIdGeneratorTestImpl("id1");

        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
        AdjustableClock clock = new AdjustableClock(date.toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacadeConfiguration().createForTests(clock, lotteryIdGeneratorTest, repository);
        TicketDto ticket = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6)).ticketDto();
        clock.plusDays(30);
        TicketDto ticket1 = numberReceiverFacade.inputNumbers(List.of(1, 2, 3, 4, 5, 6)).ticketDto();
        // when
        Optional<List<TicketDto>>allTickets = numberReceiverFacade.retrieveAllUserTicketsForNextDrawDate();

        //then
        assertThat(allTickets.get()).containsOnly(ticket1);
    }
}
