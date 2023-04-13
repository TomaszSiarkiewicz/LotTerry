package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;

public record TicketDto(
        String lotteryId,
        LocalDateTime drawDate
) {
}
