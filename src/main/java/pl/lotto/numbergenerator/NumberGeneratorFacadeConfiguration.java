package pl.lotto.numbergenerator;

public class NumberGeneratorFacadeConfiguration {

    public NumberGeneratorFacade createForTests(DrawingResultDtoRepository repository) {
        return new NumberGeneratorFacade(repository);
    }
}