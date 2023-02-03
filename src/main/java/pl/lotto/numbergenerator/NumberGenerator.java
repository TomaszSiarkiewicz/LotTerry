package pl.lotto.numbergenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

class NumberGenerator {
    Random random;

    public NumberGenerator() {
        random = new Random();
    }

    public List<Integer> generate() {
        Set<Integer> drawnNumbers = new HashSet<>();
        while (drawnNumbers.size() < 6){
            drawnNumbers.add(random.nextInt(99) +1);
        }
        return drawnNumbers.stream().sorted().collect(Collectors.toList());
    }
}
