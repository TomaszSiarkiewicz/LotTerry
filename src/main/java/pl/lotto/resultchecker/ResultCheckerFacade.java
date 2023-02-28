package pl.lotto.resultchecker;

import pl.lotto.numbergenerator.DrawingResultDto;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        return ticketResultRepository.findWinnersByDate(date);
  }
    public PlayerResultDto getWinnerByTicketId(String id){
        TicketResult ticketbyId = ticketResultRepository.findTicketbyId(id);
        return new PlayerResultDto(ticketbyId.userNumbers(), ticketbyId.drawDate(), ticketbyId.isWinner());
    }
    public void calculateWinners(LocalDateTime date){
        DrawingResultDto drawingResultDto = numberGeneratorFacade.retrieveNumbersByDate(date);
        Optional<List<Ticket>> ticketPlayed = numberReceiverFacade.getPlayedTicketDtoForGivenDate(date);
        ticketPlayed.ifPresent(tickets -> {
            ticketResultRepository.save(winnerChecker.getResults(drawingResultDto, ticketPlayed.get()));
        });
    }
}
