package pl.lotto.resultchecker;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTicketResultDatabaseImplementation implements TicketResultRepository {
    Map<String, TicketResult> lottoResultDatabase = new HashMap<>();

    @Override
    public void save(List<TicketResult> results) {
        results.forEach(ticketResult -> lottoResultDatabase.put(ticketResult.ticketId(), ticketResult));
    }

    @Override
    public List<TicketResult> findWinnersByDate(LocalDateTime date) {
        List<TicketResult> winners = new ArrayList<>();
        lottoResultDatabase.forEach((s, ticketResult) -> {
            if (ticketResult.isWinner() && ticketResult.drawDate().equals(date)) winners.add(ticketResult);
        });
        return winners;
    }

    @Override
    public TicketResult findTicketbyId(String id) {
        return lottoResultDatabase.get(id);
    }
}
