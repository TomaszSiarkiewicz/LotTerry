package pl.lotto.resultchecker;

import pl.lotto.AnnouncerResponseDto;
import pl.lotto.numbergenerator.DrawingResultDto;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.Ticket;
import pl.lotto.numberreceiver.TicketDto;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class ResultCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final WinnerChecker winnerChecker;
    private final TicketResultRepository ticketResultRepository;
    private final ResultAnnouncerFacade resultAnnouncerFacade;


    ResultCheckerFacade(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, WinnerChecker winnerChecker, TicketResultRepository ticketResultRepository, ResultAnnouncerFacade resultAnnouncerFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.winnerChecker = winnerChecker;
        this.ticketResultRepository = ticketResultRepository;
        this.resultAnnouncerFacade = resultAnnouncerFacade;
    }

    public List<TicketResult> getWinnersByDate(LocalDateTime date) {
        return ticketResultRepository.findWinnersByDrawDate(date);
    }

    public AnnouncerResponseDto getWinnerByTicketId(String id) {
        try {
            TicketDto byId = numberReceiverFacade.findById(id);
            if (getWinnersByDate(byId.drawDate()).isEmpty()){
                return new AnnouncerResponseDto("Checking to soon, no drowing for your ticket", null);
            }
            TicketResult ticketByTicketId = ticketResultRepository.findTicketByTicketId(id);
            return new AnnouncerResponseDto("Your result", new PlayerResultDto(ticketByTicketId.userNumbers(), ticketByTicketId.drawDate(), ticketByTicketId.isWinner()));
        } catch (RuntimeException e){
            return new AnnouncerResponseDto("You never played!!", null );
        }
    }

    public void calculateWinners(LocalDateTime date) {
        DrawingResultDto drawingResultDto = numberGeneratorFacade.retrieveNumbersByDate(date);
        List<Ticket> ticketPlayed = numberReceiverFacade.getPlayedTicketDtoForGivenDate(date);
        List<TicketResult> results = winnerChecker.getResults(drawingResultDto, ticketPlayed);
        if (!results.isEmpty()) {
            ticketResultRepository.saveAll(results);
            resultAnnouncerFacade.invalidateCache();
        }
    }
}

