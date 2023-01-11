package pl.lotto.numberreceiver;

import java.time.LocalDateTime;

public record TicketDto(
        String message,
        String lotteryId,
        LocalDateTime drawDate
) {
}
