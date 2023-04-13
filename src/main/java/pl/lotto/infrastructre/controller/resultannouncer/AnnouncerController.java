package pl.lotto.infrastructre.controller.resultannouncer;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;

@RestController
public class AnnouncerController {
    private final ResultAnnouncerFacade resultAnnouncerFacade;

    public AnnouncerController(ResultAnnouncerFacade resultAnnouncerFacade) {
        this.resultAnnouncerFacade = resultAnnouncerFacade;
    }

    @GetMapping("/result/{lotteryId}")
    ResponseEntity<AnnouncerResponseDto> result(@PathVariable String lotteryId) {
        AnnouncerResponseDto ticketResultById = resultAnnouncerFacade.getTicketResultById(lotteryId);
        if (ticketResultById.resultDto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ticketResultById);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ticketResultById);
    }

}
