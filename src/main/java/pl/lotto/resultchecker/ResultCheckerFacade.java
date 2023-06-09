package pl.lotto.resultchecker;

import pl.lotto.infrastructre.controller.resultannouncer.AnnouncerResponseDto;
import pl.lotto.numbergenerator.DrawingResultDto;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.Ticket;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;


public class ResultCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final WinnerChecker winnerChecker;
    private final TicketResultRepository ticketResultRepository;
    private final Clock clock;


    ResultCheckerFacade(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, WinnerChecker winnerChecker, TicketResultRepository ticketResultRepository, Clock clock) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.winnerChecker = winnerChecker;
        this.ticketResultRepository = ticketResultRepository;
        this.clock = clock;
    }

    public List<TicketResult> getWinnersByDate(LocalDateTime date) {
        return ticketResultRepository.findWinnersByDrawDate(date);
    }

    public AnnouncerResponseDto getWinnerByTicketId(String id) {
        LocalDateTime dateNow = LocalDateTime.now(clock);

        try {
            LocalDateTime drawDate = numberReceiverFacade.findById(id).drawDate();
            if (isResultNotGenerated(drawDate) || isTooEarlyToCheck(dateNow, drawDate)) {
                return new AnnouncerResponseDto("Checking to soon, no drawing for your ticket", null);
            }
            TicketResult ticketByTicketId = ticketResultRepository.findTicketByTicketId(id);
            return new AnnouncerResponseDto("Your result", new PlayerResultDto(ticketByTicketId.userNumbers(), ticketByTicketId.drawDate(), ticketByTicketId.isWinner()));
        } catch (RuntimeException e) {
            return new AnnouncerResponseDto("You never played!!", null);
        }
    }

    private boolean isTooEarlyToCheck(LocalDateTime dateNow, LocalDateTime drawDate) {
        return dateNow.isBefore(drawDate);
    }

    private boolean isResultNotGenerated(LocalDateTime drawDate) {
        return getWinnersByDate(drawDate).isEmpty();
    }

    public void calculateWinners(LocalDateTime date) {
        DrawingResultDto drawingResultDto = numberGeneratorFacade.retrieveNumbersByDate(date);
        List<Ticket> ticketPlayed = numberReceiverFacade.getPlayedTicketDtoForGivenDate(date);
        List<TicketResult> results = winnerChecker.getResults(drawingResultDto, ticketPlayed);
        if (!results.isEmpty()) {
            ticketResultRepository.saveAll(results);
        }
    }
}

