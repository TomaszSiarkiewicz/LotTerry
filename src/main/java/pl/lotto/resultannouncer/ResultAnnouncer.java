package pl.lotto.resultannouncer;

import pl.lotto.resultchecker.PlayerResultDto;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.util.HashMap;
import java.util.Map;

public class ResultAnnouncer {
    private final ResultCheckerFacade resultCheckerFacade;
    Map<String, PlayerResultDto> cache = new HashMap<>();

    public ResultAnnouncer(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }
    public PlayerResultDto getTicketResultById(String id){
        PlayerResultDto playerResult = cache.get(id);
        if (playerResult == null){
            playerResult = resultCheckerFacade.getWinnerByTicketId(id);
            cache.put(id, playerResult);
        }
        return playerResult;
    }
}
