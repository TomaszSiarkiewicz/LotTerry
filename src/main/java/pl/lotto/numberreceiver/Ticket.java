package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public record Ticket (
        String lotteryId,
        LocalDateTime drawDate,
        List<Integer> numbers
){

}
