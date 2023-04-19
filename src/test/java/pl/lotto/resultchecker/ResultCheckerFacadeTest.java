package pl.lotto.resultchecker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lotto.AdjustableClock;
import pl.lotto.infrastructre.controller.resultannouncer.AnnouncerResponseDto;
import pl.lotto.infrastructre.numbergeneratorclient.DrawingResultDto;
import pl.lotto.infrastructre.numbergeneratorclient.NumberGeneratorClient;
import pl.lotto.infrastructre.numbergeneratorclient.NumberGeneratorClientImpl;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.Ticket;
import pl.lotto.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ResultCheckerFacadeTest {
    InMemoryTicketResultDatabaseImplementation ticketResultRepository = new InMemoryTicketResultDatabaseImplementation();
    NumberGeneratorClient numberGeneratorClient = mock(NumberGeneratorClientImpl.class);
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    AdjustableClock clock =new AdjustableClock(LocalDateTime.of(2023, 3, 5, 10, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, ticketResultRepository,clock);
    LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
    DrawingResultDto drawingResultDto = new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6));

    @BeforeEach
    public void cleanUp() {
        ticketResultRepository.deleteAll();
    }

    @Test
    public void should_return_one_winner_when_one_winning_numbers_played() {
        //given
        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
        Ticket loosingTicket = new Ticket("id2", date, List.of(32, 2, 1, 15, 16, 17));
        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket, loosingTicket));

        TicketResult givenWinner1 = new TicketResult(winningTicket.lotteryId(), winningTicket.numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        TicketResult givenlooser1 = new TicketResult(loosingTicket.lotteryId(), loosingTicket.numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, false);



        //when
        resultCheckerFacade.calculateWinners(drawingResultDto);

        List<TicketResult> winners = resultCheckerFacade.getWinnersByDate(date);

        //then
        assertAll(
                () -> assertThat(winners.size()).isEqualTo(2),
                () -> assertThat(winners).contains(givenWinner1, givenlooser1)
        );
    }

    @Test
    public void should_return_none_winners_when_no_winning_numbers_played() {
        //given
        Ticket loosingTicket1 = new Ticket("id1", date, List.of(14, 23, 34, 45, 5, 6));
        Ticket loosingTicket2 = new Ticket("id2", date, List.of(32, 2, 1, 15, 16, 17));

        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(loosingTicket1, loosingTicket2));

        //when
        resultCheckerFacade.calculateWinners(drawingResultDto);

        List<TicketResult> winners = resultCheckerFacade.getWinnersByDate(date);
        //then
        assertAll(
                () -> assertThat(winners.size()).isEqualTo(2),
                () -> assertThat(winners.get(0).isWinner()).isFalse(),
                () -> assertThat(winners.get(1).isWinner()).isFalse()
        );
    }

    @Test
    public void should_return_list_of_winning_tickets_when_more_then_one_winner() {
        //given
        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
        Ticket winningTicket2 = new Ticket("id2", date, List.of(1, 2, 3, 4, 5, 6));
        Ticket loosingTicket = new Ticket("id3", date, List.of(32, 2, 1, 15, 16, 17));

        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket, loosingTicket, winningTicket2));
        //when
        resultCheckerFacade.calculateWinners(drawingResultDto);

        TicketResult givenWinner = new TicketResult(winningTicket.lotteryId(), winningTicket.numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        TicketResult givenWinner2 = new TicketResult(winningTicket2.lotteryId(), winningTicket2.numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        //then
        assertThat(resultCheckerFacade.getWinnersByDate(date).size()).isEqualTo(3);
        assertThat(resultCheckerFacade.getWinnersByDate(date)).contains(givenWinner, givenWinner2);
    }

    @Test
    public void should_return_lottoResult_by_ticket_id_when_winner() {
        //given
        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket));
        when(numberReceiverFacade.findById(anyString())).thenReturn(new TicketDto("id1", date));
        resultCheckerFacade.calculateWinners(drawingResultDto);

        //when
        AnnouncerResponseDto result = resultCheckerFacade.getWinnerByTicketId("id1");

        //then
        assertThat(result.message()).isEqualTo("Your result");
    }

    @Test
    public void should_return_lottoResult_by_ticket_id_when_looser() {
        //given
        Ticket looserTicket = new Ticket("id1", date, List.of(12, 11, 31, 41, 5, 6));
        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(looserTicket));
        when(numberReceiverFacade.findById(anyString())).thenReturn(new TicketDto("id1", date));
        resultCheckerFacade.calculateWinners(drawingResultDto);

        //when

        AnnouncerResponseDto result = resultCheckerFacade.getWinnerByTicketId("id1");

        //then
        assertThat(result.message()).isEqualTo("Your result");
    }

    @Test
    public void should_return_winning_ticket_when_different_numbers_combinations_played() {
        //given
        List<Integer> oneCorrectNumber = List.of(1, 22, 33, 44, 55, 66);
        List<Integer> twoCorrectNumbers = List.of(1, 2, 33, 44, 55, 66);
        List<Integer> threeCorrectNumbers = List.of(1, 2, 3, 44, 55, 66);
        List<Integer> fourCorrectNumbers = List.of(1, 2, 3, 4, 55, 66);
        List<Integer> fiveCorrectNumbers = List.of(1, 2, 3, 4, 5, 66);
        List<Integer> sixCorrectNumbers = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> zeroCorrectNumbers = List.of(11, 22, 33, 44, 55, 66);
        List<Integer> drawingResults = List.of(1, 2, 3, 4, 5, 6);
        List<Ticket> playedTickets = List.of(
                new Ticket("id1", date, oneCorrectNumber),
                new Ticket("id2", date, twoCorrectNumbers),
                new Ticket("id3", date, threeCorrectNumbers),
                new Ticket("id4", date, fourCorrectNumbers),
                new Ticket("id5", date, fiveCorrectNumbers),
                new Ticket("id6", date, sixCorrectNumbers),
                new Ticket("id0", date, zeroCorrectNumbers)
        );



        when(numberGeneratorClient.retrieveNumbersByDate(date)).thenReturn(drawingResultDto);
        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(any())).thenReturn(playedTickets);

        TicketResult givenWinner1 = new TicketResult(playedTickets.get(2).lotteryId(), playedTickets.get(2).numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        TicketResult givenWinner2 = new TicketResult(playedTickets.get(3).lotteryId(), playedTickets.get(3).numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        TicketResult givenWinner3 = new TicketResult(playedTickets.get(4).lotteryId(), playedTickets.get(4).numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);
        TicketResult givenWinner4 = new TicketResult(playedTickets.get(5).lotteryId(), playedTickets.get(5).numbers(), numberGeneratorClient.retrieveNumbersByDate(date).numbers(), date, true);

        //when
        resultCheckerFacade.calculateWinners(drawingResultDto);
        //then
        assertThat(resultCheckerFacade.getWinnersByDate(date).size()).isEqualTo(7);
        assertThat(resultCheckerFacade.getWinnersByDate(date)).contains(givenWinner3, givenWinner4, givenWinner1, givenWinner2);
    }
}
