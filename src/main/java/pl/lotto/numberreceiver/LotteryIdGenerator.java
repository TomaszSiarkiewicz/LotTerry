package pl.lotto.numberreceiver;

import java.util.UUID;

class LotteryIdGenerator implements LotteryIdGenerable {

    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
