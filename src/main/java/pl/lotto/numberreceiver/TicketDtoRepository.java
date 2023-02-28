package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketDtoRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllByDrawDate(LocalDateTime nextDrawDate);

    Optional<Ticket> findById(String id);
}
