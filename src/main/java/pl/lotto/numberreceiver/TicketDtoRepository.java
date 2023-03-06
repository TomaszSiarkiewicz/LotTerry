package pl.lotto.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketDtoRepository extends MongoRepository<Ticket, String> {
//    Ticket save(Ticket ticket);
//    Optional<Ticket> findById(String id);
List<Ticket> findAllByDrawDate(LocalDateTime nextDrawDate);
}
