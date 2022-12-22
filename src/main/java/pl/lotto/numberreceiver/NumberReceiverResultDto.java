package pl.lotto.numberreceiver;

import java.time.LocalDateTime;

record NumberReceiverResultDto(
        String message,
        String lotteryId,
        LocalDateTime drawDate
) {
}
