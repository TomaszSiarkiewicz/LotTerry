package pl.lotto;


import org.springframework.web.bind.annotation.*;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.resultchecker.PlayerResultDto;

@RestController
public class AnnouncerController {
    private final ResultAnnouncerFacade resultAnnouncerFacade;

    public AnnouncerController(ResultAnnouncerFacade resultAnnouncerFacade) {
        this.resultAnnouncerFacade = resultAnnouncerFacade;
    }

    @GetMapping("/lottery")
    AnnouncerResponseDto result(@RequestParam("lotteryid") String lotteryid) {
        return resultAnnouncerFacade.getTicketResultById(lotteryid);
    }

}
