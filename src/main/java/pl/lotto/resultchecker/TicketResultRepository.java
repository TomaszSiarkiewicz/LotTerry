package pl.lotto.resultchecker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketResultRepository {
    void save(List<TicketResult> results);

    List<TicketResult> findWinnersByDate(LocalDateTime date);

    TicketResult findTicketbyId(String id);
}
