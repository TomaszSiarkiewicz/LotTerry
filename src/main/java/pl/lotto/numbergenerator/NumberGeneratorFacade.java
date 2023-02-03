package pl.lotto.numbergenerator;

import java.time.LocalDate;
import java.util.List;

class NumberGeneratorFacade {
    NumberGenerator numberGenerator;
    DrawingResultDtoRepository repository;

    public NumberGeneratorFacade(DrawingResultDtoRepository repository) {
        this.repository = repository;
        numberGenerator = new NumberGenerator();
    }

    public DrawingResultDto generateNumbersAndSave(LocalDate date) {
        List<Integer> numbers = numberGenerator.generate();
        return repository.save(new DrawingResultDto(date, numbers));
    }

    public DrawingResultDto retrieveNumbersByDate(LocalDate date) {
        return repository.getByDate(date);
    }
}
