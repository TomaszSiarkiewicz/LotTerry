package pl.lotto;


import org.springframework.web.bind.annotation.*;
import pl.lotto.numberreceiver.InputNumbersResponse;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.resultchecker.PlayerResultDto;

import java.util.List;

@RestController
public class NumberReciverController {
    private final NumberReceiverFacade numberReceiver;

    public NumberReciverController(NumberReceiverFacade numberReceiver) {
        this.numberReceiver = numberReceiver;
    }
    @GetMapping("/newlottery/{numbers}")
    InputNumbersResponse result(@PathVariable Integer[] numbers ) {
        return numberReceiver.inputNumbers(List.of(numbers));
    }

}
