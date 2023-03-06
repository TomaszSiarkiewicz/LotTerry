package pl.lotto.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class NumberReceiverFacadeConfiguration {
    @Bean
    public NumberReceiverFacade numberReciverFacade(Clock clock, LotteryIdGenerable lotteryIdGenerator, TicketDtoRepository repository) {
        NextDrawDateCalculator nextDrawDateCalculator = new NextDrawDateCalculator(clock);
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(nextDrawDateCalculator, numberValidator, lotteryIdGenerator, repository);
    }
    @Bean
    LotteryIdGenerable lotteryIdGenerable(){
        return new LotteryIdGenerator();
    }
    @Bean
    Clock clock(){
        return Clock.systemUTC();
    }

}
