package pl.lotto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numbergenerator.NumberGeneratorFacade;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.time.LocalDateTime;

@RestController
public class Testcontrol {
    private final ResultCheckerFacade checkerFacade;
    private final NumberGeneratorFacade generatorFacade;

    public Testcontrol(ResultCheckerFacade checkerFacade, NumberGeneratorFacade generatorFacade) {
        this.checkerFacade = checkerFacade;
        this.generatorFacade = generatorFacade;
    }

    @GetMapping("/generate")
    void generate(){
        generatorFacade.generateNumbersAndSave();
        checkerFacade.calculateWinners(LocalDateTime.of(2023,03,11,12,0,0,0));
    }
}
