package pl.lotto.numberreceiver;

import java.util.List;

class NumberValidator {
    private final int MAX_NUMBERS_FROM_USER = 6;
    private final int MAX_NUMBER = 99;
    private final int MIN_NUMBER = 1;

    public NumberValidator() {
    }

    public boolean validate(List<Integer> numbersFromUser) {
        return isSixNumbersFromUser(numbersFromUser) && !isNumbersFromUserDuplicate(numbersFromUser) && !isNumbersFromUserOutOfBound(numbersFromUser);
    }

    private boolean isNumbersFromUserOutOfBound(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .filter(givenNumber -> givenNumber <= MAX_NUMBER && givenNumber >= MIN_NUMBER)
                .toList();
        return result.size() != 6;
    }

    private boolean isNumbersFromUserDuplicate(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .distinct()
                .toList();
        return result.size() != 6;
    }

    private boolean isSixNumbersFromUser(List<Integer> numbersFromUser) {
        return numbersFromUser.size() == MAX_NUMBERS_FROM_USER;
    }
}
