//package pl.lotto.resultchecker;
//
//import org.junit.jupiter.api.Test;
//import pl.lotto.numbergenerator.DrawingResultDto;
//import pl.lotto.numbergenerator.NumberGeneratorFacade;
//import pl.lotto.numberreceiver.NumberReceiverFacade;
//import pl.lotto.numberreceiver.Ticket;
//import pl.lotto.resultannouncer.ResultAnnouncerFacade;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class ResultCheckerFacadeTest {
//    InMemoryTicketResultDatabaseImplementation ticketResultRepository = new InMemoryTicketResultDatabaseImplementation();
//
//    @Test
//    public void should_return_one_winner_when_one_winning_numbers_played() {
//        //given
//        WinnerChecker winnerChecker = new WinnerChecker();
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
//        Ticket loosingTicket = new Ticket("id2", date, List.of(32, 2, 1, 15, 16, 17));
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6)));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket, loosingTicket));
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository, resultAnnouncerFacade);
//
//        //when
//        resultCheckerFacade.calculateWinners(date);
//
//        List<TicketResult> winners = resultCheckerFacade.getWinnersByDate(date);
//        TicketResult givenWinner = new TicketResult(winningTicket.lotteryId(), winningTicket.numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        //then
//        assertThat(winners).containsOnly(givenWinner);
//    }
//
//    @Test
//    public void should_return_none_winners_when_no_winning_numbers_played() {
//        //given
//        WinnerChecker winnerChecker = new WinnerChecker();
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        Ticket winningTicket = new Ticket("id1", date, List.of(14, 23, 34, 45, 5, 6));
//        Ticket loosingTicket = new Ticket("id2", date, List.of(32, 2, 1, 15, 16, 17));
//
//        //when
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6)));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket, loosingTicket));
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository,resultAnnouncerFacade);
//        resultCheckerFacade.calculateWinners(date);
//
//        List<TicketResult> winners = resultCheckerFacade.getWinnersByDate(date);
//        //then
//        assertThat(winners).isEmpty();
//    }
//
//    @Test
//    public void should_return_list_of_winning_tickets_when_more_then_one_winner() {
//        //given
//        WinnerChecker winnerChecker = new WinnerChecker();
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
//        Ticket winningTicket2 = new Ticket("id2", date, List.of(1, 2, 3, 4, 5, 6));
//        Ticket loosingTicket = new Ticket("id3", date, List.of(32, 2, 1, 15, 16, 17));
//
//        //when
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6)));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(date)).thenReturn(List.of(winningTicket, loosingTicket, winningTicket2));
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository,resultAnnouncerFacade);
//        resultCheckerFacade.calculateWinners(date);
//
//        List<TicketResult> winners = resultCheckerFacade.getWinnersByDate(date);
//        TicketResult givenWinner = new TicketResult(winningTicket.lotteryId(), winningTicket.numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        TicketResult givenWinner2 = new TicketResult(winningTicket2.lotteryId(), winningTicket2.numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        //then
//        assertThat(winners).containsOnly(givenWinner, givenWinner2);
//    }
//
//    @Test
//    public void should_return_lottoResult_by_ticket_id_when_winner() {
//        //given
//        WinnerChecker winnerChecker = new WinnerChecker();
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        Ticket winningTicket = new Ticket("id1", date, List.of(1, 2, 3, 4, 5, 6));
//        //when
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6)));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(any())).thenReturn(List.of(winningTicket));
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository,resultAnnouncerFacade);
//        resultCheckerFacade.calculateWinners(date);
//
//        PlayerResultDto ticketResult = resultCheckerFacade.getWinnerByTicketId("id1");
//        PlayerResultDto givenWinner = new PlayerResultDto(numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//
//        assertThat(ticketResult).isEqualTo(givenWinner);
//    }
//
//    @Test
//    public void should_return_lottoResult_by_ticket_id_when_looser() {
//        //given
//        WinnerChecker winnerChecker = new WinnerChecker();
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        Ticket looserTicket = new Ticket("id1", date, List.of(12, 11, 31, 41, 5, 6));
//        //when
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, List.of(1, 2, 3, 4, 5, 6)));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(any())).thenReturn(List.of(looserTicket));
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository,resultAnnouncerFacade);
//        resultCheckerFacade.calculateWinners(date);
//
//        PlayerResultDto ticketResult = resultCheckerFacade.getWinnerByTicketId("id1");
//        PlayerResultDto givenLooser = new PlayerResultDto(looserTicket.numbers(), date, false);
//
//        //then
//        assertThat(ticketResult).isEqualTo(givenLooser);
//    }
//
//    @Test
//    public void should_return_winning_ticket_when_different_numbers_combinations_played() {
//        //given
//        List<Integer> oneCorrectNumber = List.of(1, 22, 33, 44, 55, 66);
//        List<Integer> twoCorrectNumbers = List.of(1, 2, 33, 44, 55, 66);
//        List<Integer> threeCorrectNumbers = List.of(1, 2, 3, 44, 55, 66);
//        List<Integer> fourCorrectNumbers = List.of(1, 2, 3, 4, 55, 66);
//        List<Integer> fiveCorrectNumbers = List.of(1, 2, 3, 4, 5, 66);
//        List<Integer> sixCorrectNumbers = List.of(1, 2, 3, 4, 5, 6);
//        List<Integer> zeroCorrectNumbers = List.of(11, 22, 33, 44, 55, 66);
//        List<Integer> drawingResults = List.of(1, 2, 3, 4, 5, 6);
//        LocalDateTime date = LocalDateTime.of(2022, 12, 1, 12, 0, 0);
//        List<Ticket> playedTickets = List.of(
//                new Ticket("id1", date, oneCorrectNumber),
//                new Ticket("id2", date, twoCorrectNumbers),
//                new Ticket("id3", date, threeCorrectNumbers),
//                new Ticket("id4", date, fourCorrectNumbers),
//                new Ticket("id5", date, fiveCorrectNumbers),
//                new Ticket("id6", date, sixCorrectNumbers),
//                new Ticket("id0", date, zeroCorrectNumbers)
//        );
//
//
//        WinnerChecker winnerChecker = new WinnerChecker();
//        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
//        ResultAnnouncerFacade resultAnnouncerFacade = mock(ResultAnnouncerFacade.class);
//        NumberGeneratorFacade numberGeneratorFacade = mock(NumberGeneratorFacade.class);
//        when(numberGeneratorFacade.retrieveNumbersByDate(date)).thenReturn(new DrawingResultDto(date, drawingResults));
//        when(numberReceiverFacade.getPlayedTicketDtoForGivenDate(any())).thenReturn(playedTickets);
//
//        TicketResult givenWinner1 = new TicketResult(playedTickets.get(2).lotteryId(), playedTickets.get(2).numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        TicketResult givenWinner2 = new TicketResult(playedTickets.get(3).lotteryId(), playedTickets.get(3).numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        TicketResult givenWinner3 = new TicketResult(playedTickets.get(4).lotteryId(), playedTickets.get(4).numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//        TicketResult givenWinner4 = new TicketResult(playedTickets.get(5).lotteryId(), playedTickets.get(5).numbers(), numberGeneratorFacade.retrieveNumbersByDate(date).numbers(), date, true);
//
//        //when
//        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().resultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,ticketResultRepository,resultAnnouncerFacade);
//        resultCheckerFacade.calculateWinners(date);
//        //then
//        assertThat(resultCheckerFacade.getWinnersByDate(date)).containsOnly(givenWinner1, givenWinner2, givenWinner3, givenWinner4);
//    }
//}
