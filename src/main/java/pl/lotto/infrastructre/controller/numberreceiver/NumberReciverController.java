package pl.lotto.infrastructre.controller.numberreceiver;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.InputNumbersResponseDto;
import pl.lotto.numberreceiver.dto.NumberInputRequestDto;

@RestController
public class NumberReciverController {
    private final NumberReceiverFacade numberReceiver;

    public NumberReciverController(NumberReceiverFacade numberReceiver) {
        this.numberReceiver = numberReceiver;
    }

    @PostMapping("/lottery")
    ResponseEntity<InputNumbersResponseDto> result(@RequestBody NumberInputRequestDto requestDto) {
        InputNumbersResponseDto response = numberReceiver.inputNumbers(requestDto.numbers());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
