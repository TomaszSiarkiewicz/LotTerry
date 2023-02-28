package pl.lotto.numbergenerator;

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
        WinningNumbers winningNumbers = repository.save(new WinningNumbers(numberReceiverFacade.getNextDrawingDate(), numbers));
        return new DrawingResultDto(winningNumbers.date(), winningNumbers.numbers());
    }

    public DrawingResultDto retrieveNumbersByDate(LocalDateTime date) {
        WinningNumbers winningNumbers = repository.findByDate(date).orElseThrow(() -> new RuntimeException("winning number not found"));
        return new DrawingResultDto(winningNumbers.date(),winningNumbers.numbers());
    }
}
