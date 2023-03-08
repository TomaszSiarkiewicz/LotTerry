package pl.lotto.infrastructre.controller.numberreceiver;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.InputNumbersResponse;
import pl.lotto.numberreceiver.NumberReceiverFacade;

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
