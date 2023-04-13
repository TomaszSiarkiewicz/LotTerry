package pl.lotto.numbergenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import pl.lotto.numberreceiver.NumberReceiverFacade;

@Log4j2
public class NumberGeneratorFacade {
    private final NumberGenerator numberGenerator;
    private final WinningNumbersRepository repository;
    private final NumberReceiverFacade numberReceiverFacade;
    private LocalDateTime drawingDate;


    public NumberGeneratorFacade(WinningNumbersRepository repository, NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
        this.repository = repository;
        numberGenerator = new NumberGenerator();
        drawingDate = numberReceiverFacade.getNextDrawingDate();
    }

    public DrawingResultDto generateNumbersAndSave() {
        LocalDateTime nextDrawingDate = numberReceiverFacade.getNextDrawingDate();
        drawingDate = nextDrawingDate;
        Optional<WinningNumbers> byDate1 = repository.findByDate(nextDrawingDate);

        if (byDate1.isPresent()) {
            WinningNumbers winningNumbers1 = byDate1.get();
            log.info("numbers was already generated for: " + winningNumbers1.date());
            return new DrawingResultDto(winningNumbers1.date(), winningNumbers1.numbers());
        }

        List<Integer> numbers = numberGenerator.generate()
                .stream()
                .toList();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .numbers(numbers)
                .date(nextDrawingDate)
                .build();
        WinningNumbers saved = repository.save(winningNumbers);
        return new DrawingResultDto(saved.date(), saved.numbers());
    }

    public DrawingResultDto retrieveNumbersByDate(LocalDateTime date) {
        WinningNumbers winningNumbers = repository.findByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("winning number not found"));
        return new DrawingResultDto(winningNumbers.date(), winningNumbers.numbers());
    }

    public LocalDateTime getDrawingDate() {
        return drawingDate;
    }
}
