package pl.lotto.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketResultRepository extends MongoRepository<TicketResult, String> {
//    void saveAll(List<TicketResult> results);

    List<TicketResult> findWinnersByDrawDate(LocalDateTime date);


    TicketResult findTicketByTicketId(String ticketId);
}
