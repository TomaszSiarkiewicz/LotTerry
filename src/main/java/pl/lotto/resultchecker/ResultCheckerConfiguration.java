package pl.lotto.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.infrastructre.numbergeneratorclient.NumberGeneratorClient;
import pl.lotto.numberreceiver.NumberReceiverFacade;

import java.time.Clock;

@Configuration
public class ResultCheckerConfiguration {
    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade, TicketResultRepository ticketResultRepository, Clock clock) {
        return new ResultCheckerFacade(numberReceiverFacade, new WinnerChecker(), ticketResultRepository, clock);
    }
}
