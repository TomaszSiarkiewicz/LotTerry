package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InMemoryTicketDtoDatabaseRepositoryImplementation implements TicketDtoRepository {
    private Map<String, Ticket> ticketsDtoDatabase = new HashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        ticketsDtoDatabase.put(ticket.lotteryId(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllByDrawDate(LocalDateTime drawDate) {
        return ticketsDtoDatabase.values()
                .stream()
                .filter(ticket -> ticket.drawDate().equals(drawDate))
                .collect(Collectors.toList());

    }
}
