package pl.lotto.numberreceiver;

import java.time.LocalDateTime;

public record NumberReceiverResultDto(
        String message,
        String lotteryId,
        LocalDateTime drawDate
) {
}
