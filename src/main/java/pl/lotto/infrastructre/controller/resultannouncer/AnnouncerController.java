package pl.lotto.infrastructre.controller.resultannouncer;


import org.springframework.web.bind.annotation.*;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;

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
