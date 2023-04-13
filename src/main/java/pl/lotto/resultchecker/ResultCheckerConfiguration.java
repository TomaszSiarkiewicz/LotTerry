package pl.lotto.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.numberreceiver.NumberReceiverFacade;

import java.time.Clock;

@Configuration
public class ResultCheckerConfiguration {
    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, NumberGeneratorFacade numberGeneratorFacade, TicketResultRepository ticketResultRepository, Clock clock) {
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade, new WinnerChecker(), ticketResultRepository, clock);
    }
}
