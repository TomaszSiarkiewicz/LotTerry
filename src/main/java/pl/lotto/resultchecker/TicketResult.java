package pl.lotto.resultchecker;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document
public record TicketResult(
        @Id
        String ticketId,
        List<Integer> userNumbers,
        List<Integer> winningNumbers,
        LocalDateTime drawDate,
        boolean isWinner
){
}
