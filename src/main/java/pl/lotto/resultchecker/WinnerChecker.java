package pl.lotto.resultchecker;

import pl.lotto.numbergenerator.DrawingResultDto;
import pl.lotto.numberreceiver.Ticket;

import java.util.ArrayList;
import java.util.List;

class WinnerChecker {


    public WinnerChecker() {
    }

    List<TicketResult> getResults(DrawingResultDto drawingResultDto, List<Ticket> userTickets) {
        List<TicketResult> winners = new ArrayList<>();
        userTickets.forEach(ticket -> winners.add(checkNumbers(ticket, drawingResultDto)));
        return winners;
    }

    private TicketResult checkNumbers(Ticket ticket, DrawingResultDto drawingResultDto) {
        List<Integer> matchingNumbers = drawingResultDto.numbers().stream().filter(ticket.numbers()::contains).toList();
            return mapToLottoResult(ticket, drawingResultDto, isWinner(matchingNumbers));

    }

    private TicketResult mapToLottoResult(Ticket ticket, DrawingResultDto drawingResultDto, boolean isWinner) {
        return new TicketResult(ticket.lotteryId(), ticket.numbers(), drawingResultDto.numbers(), drawingResultDto.date(), isWinner);
    }

    private boolean isWinner(List<Integer> matchingNumbers) {
        return matchingNumbers.size() >= 3;
    }
}
