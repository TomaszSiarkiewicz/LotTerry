package pl.lotto.infrastructre.scheduler.resultchecker;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.infrastructre.numbergeneratorclient.DrawingResultDto;
import pl.lotto.infrastructre.numbergeneratorclient.NumberGeneratorClientImpl;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
@Log4j2
@AllArgsConstructor
public class ResultCheckerScheduler {
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResultAnnouncerFacade announcerFacade;
    private final NumberGeneratorClientImpl numberGeneratorClient;
    private final Clock clock;

    @Scheduled(cron = "${lotto.result-checker.RunOccurrence}")
    public void runResultChecker() {
        log.info("Result checker started");
        LocalDateTime drawDateTime = LocalDateTime.now(clock).withHour(12).withMinute(0).withSecond(0).withNano(0);
        DrawingResultDto drawingResultDto = null;
        try {
            drawingResultDto = numberGeneratorClient.retrieveNumbersByDate(drawDateTime);
        } catch (WinningNumbersNotFoundException e) {
            log.error("Can't calculate winners! No winning numbers found.");
            return;
        }
        log.info("calculating winners...");
        if (drawingResultDto != null) {
            resultCheckerFacade.calculateWinners(drawingResultDto);
            announcerFacade.invalidateCache();
        }
    }
}
