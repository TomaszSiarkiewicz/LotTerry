package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketDtoRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllByDrawDate(LocalDateTime nextDrawDate);
}
