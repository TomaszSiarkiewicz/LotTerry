package pl.lotto.numberreceiver;

import java.time.LocalDateTime;

public record TicketDto(
        String lotteryId,
        LocalDateTime drawDate
) {
}
