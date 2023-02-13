package pl.lotto.numbergenerator;

import pl.lotto.numberreceiver.NumberReceiverFacade;

import java.time.LocalDate;
import java.util.Set;

public class NumberGeneratorFacade {
    NumberGenerator numberGenerator;
    WinningNumbersRepository repository;
    NumberReceiverFacade numberReceiverFacade;

    public NumberGeneratorFacade(WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        numberGenerator = new NumberGenerator();
    }

    public DrawingResultDto generateNumbersAndSave() {

        Set<Integer> numbers = numberGenerator.generate();
        WinningNumbers winningNumbers = repository.save(new WinningNumbers(numberReceiverFacade.getNextDrawingDate(), numbers));
        return new DrawingResultDto(winningNumbers.date(), winningNumbers.numbers());
    }

    public DrawingResultDto retrieveNumbersByDate(LocalDate date) {
        WinningNumbers winningNumbers = repository.findByDate(date).orElseThrow(() -> new RuntimeException("winning number not found"));
        return new DrawingResultDto(winningNumbers.date(),winningNumbers.numbers());
    }
}
