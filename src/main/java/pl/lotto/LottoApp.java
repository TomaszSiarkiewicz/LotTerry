package pl.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LottoApp {
    public static void main(String[] args) {
        SpringApplication.run(LottoApp.class, args);
    }
}
