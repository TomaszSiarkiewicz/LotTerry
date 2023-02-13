package pl.lotto.numbergenerator;

import pl.lotto.numberreceiver.NumberReceiverFacade;

public class NumberGeneratorFacadeConfiguration {

    public NumberGeneratorFacade createForTests(WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        return new NumberGeneratorFacade(repository, numberReceiverFacade);
    }
}