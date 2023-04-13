package pl.lotto.infrastructre.scheduler.resultanouncer;

import org.springframework.scheduling.annotation.Scheduled;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;

public class ResultAnnouncerScheduler {
    private final ResultAnnouncerFacade announcerFacade;

    public ResultAnnouncerScheduler(ResultAnnouncerFacade announcerFacade) {
        this.announcerFacade = announcerFacade;
    }

    @Scheduled(cron = "${lotto.result-announcer.cacheClearRate}")
    public void clearAnnouncerCache() {
        announcerFacade.invalidateCache();
    }
}
