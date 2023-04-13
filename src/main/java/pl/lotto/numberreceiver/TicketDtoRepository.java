package pl.lotto.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TicketDtoRepository extends MongoRepository<Ticket, String> {
List<Ticket> findAllByDrawDate(LocalDateTime nextDrawDate);
}
