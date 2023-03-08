package pl.lotto.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;

@Configuration
public class ResultCheckerConfiguration {
    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, TicketResultRepository ticketResultRepository, ResultAnnouncerFacade resultAnnouncerFacade){
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade, new WinnerChecker(), ticketResultRepository, resultAnnouncerFacade);
    }
}
