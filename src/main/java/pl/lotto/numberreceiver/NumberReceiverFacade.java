package pl.lotto.numberreceiver;

import java.util.List;

public class NumberReceiverFacade {

    NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        return new NumberReceiverResultDto("correct");
    }
}
