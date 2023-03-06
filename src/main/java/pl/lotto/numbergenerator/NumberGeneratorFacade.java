package pl.lotto.numbergenerator;

import org.springframework.stereotype.Component;
import pl.lotto.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.List;
public class NumberGeneratorFacade {
    private final NumberGenerator numberGenerator;
    private final WinningNumbersRepository repository;
    private final NumberReceiverFacade numberReceiverFacade;

    public NumberGeneratorFacade(WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        numberGenerator = new NumberGenerator();
    }

    public DrawingResultDto generateNumbersAndSave() {

        List<Integer> numbers = numberGenerator.generate().stream().toList();
        WinningNumbers winningNumbers = WinningNumbers.builder().numbers(numbers).date(numberReceiverFacade.getNextDrawingDate()).build();
        WinningNumbers saved = repository.save(winningNumbers);
        return new DrawingResultDto(saved.date(), saved.numbers());
    }

    public DrawingResultDto retrieveNumbersByDate(LocalDateTime date) {
        WinningNumbers winningNumbers = repository.findByDate(date).orElseThrow(() -> new RuntimeException("winning number not found"));
        return new DrawingResultDto(winningNumbers.date(),winningNumbers.numbers());
    }
}
