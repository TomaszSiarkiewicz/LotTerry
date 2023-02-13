package pl.lotto.numbergenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class NumberGenerator {
    Random random;

    public NumberGenerator() {
        random = new Random();
    }

    public Set<Integer> generate() {
        Set<Integer> drawnNumbers = new HashSet<>();
        while (drawnNumbers.size() < 6) {
            drawnNumbers.add(random.nextInt(99) + 1);
        }
        return drawnNumbers;
    }
}
