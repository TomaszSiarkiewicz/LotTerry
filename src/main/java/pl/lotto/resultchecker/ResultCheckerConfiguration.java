package pl.lotto.resultchecker;

import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;

public class ResultCheckerConfiguration {
    public ResultCheckerFacade createForTests(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, WinnerChecker winnerChecker,TicketResultRepository ticketResultRepository ){
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade, winnerChecker, ticketResultRepository);
    }
}
