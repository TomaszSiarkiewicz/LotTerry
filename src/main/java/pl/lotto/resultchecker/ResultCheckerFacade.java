package pl.lotto.resultchecker;

import pl.lotto.numbergenerator.DrawingResultDto;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.Ticket;

import java.time.LocalDateTime;
import java.util.List;


public class ResultCheckerFacade {
    private final NumberReceiverFacade numberReceiverFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;
    private final WinnerChecker winnerChecker;
    private final TicketResultRepository ticketResultRepository;


    ResultCheckerFacade(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, WinnerChecker winnerChecker, TicketResultRepository ticketResultRepository) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.numberGeneratorFacade = numberGeneratorFacade;
        this.winnerChecker = winnerChecker;
        this.ticketResultRepository = ticketResultRepository;
    }

    public List<TicketResult> getWinnersByDate(LocalDateTime date) {
        return ticketResultRepository.findWinnersByDrawDate(date);
    }

    public PlayerResultDto getWinnerByTicketId(String id) {
        TicketResult ticketbyId = ticketResultRepository.findTicketByTicketId(id);
        return new PlayerResultDto(ticketbyId.userNumbers(), ticketbyId.drawDate(), ticketbyId.isWinner());
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

