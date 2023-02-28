package pl.lotto.resultchecker;

import java.time.LocalDateTime;
import java.util.List;

public record TicketResult(
        String ticketId,
        List<Integer> userNumbers,
        List<Integer> winningNumbers,
        LocalDateTime drawDate,
        boolean isWinner
){
}
