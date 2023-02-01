package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

record Ticket (
        String lotteryId,
        LocalDateTime drawDate,
        List<Integer> numbers
){

}
