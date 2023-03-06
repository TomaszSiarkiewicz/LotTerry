package pl.lotto.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.numberreceiver.NumberReceiverFacade;

@Configuration
public class NumberGeneratorFacadeConfiguration {
    @Bean
    public NumberGeneratorFacade numberGeneratorFacade(WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        return new NumberGeneratorFacade(repository, numberReceiverFacade);
    }
}