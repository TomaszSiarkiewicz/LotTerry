package pl.lotto.numberreceiver;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document
public record Ticket (
        @Id
        String lotteryId,
        LocalDateTime drawDate,
        List<Integer> numbers
){

}
