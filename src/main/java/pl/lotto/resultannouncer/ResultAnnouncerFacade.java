package pl.lotto.resultannouncer;

import org.springframework.stereotype.Component;
import pl.lotto.AnnouncerResponseDto;
import pl.lotto.resultchecker.PlayerResultDto;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResultAnnouncerFacade {
    private final ResultCheckerFacade resultCheckerFacade;
    Map<String, AnnouncerResponseDto> cache = new HashMap<>();

    public ResultAnnouncerFacade(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }

    public AnnouncerResponseDto getTicketResultById(String id) {
        AnnouncerResponseDto playerResult = cache.get(id);
        if (playerResult == null) {
            playerResult = resultCheckerFacade.getWinnerByTicketId(id);
            cache.put(id, playerResult);
        }
        return playerResult;
    }

    public void invalidateCache() {
        cache.clear();
    }
}
