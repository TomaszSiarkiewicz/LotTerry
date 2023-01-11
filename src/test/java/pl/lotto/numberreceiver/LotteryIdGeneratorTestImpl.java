package pl.lotto.numberreceiver;

public class LotteryIdGeneratorTestImpl implements LotteryIdGenerable {

    String id;

    LotteryIdGeneratorTestImpl(String id) {
        this.id = id;
    }

    public String generateId() {
        return id;
    }
}
