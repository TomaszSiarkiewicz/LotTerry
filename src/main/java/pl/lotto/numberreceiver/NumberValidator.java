package pl.lotto.numberreceiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static pl.lotto.numberreceiver.Constants.*;
import static pl.lotto.numberreceiver.ValidationMessage.*;

class NumberValidator {
    List<ValidationMessage> errorList = new ArrayList<>();

    public NumberValidator() {
    }

    public ValidationResult validate(List<Integer> numbersFromUser) {

        isNumbersFromUserInBound(numbersFromUser);
        isNumbersFromUserDistinctive(numbersFromUser);
        isNotLessThanSixNumbersFromUser(numbersFromUser);
        isNotMoreThanSixNumbersFromUser(numbersFromUser);

        if (errorList.isEmpty()) {
            return new ValidationResult(true, CORRECT_MESSAGE.getMessage());
        } else {
            return new ValidationResult(false, joinMessages());
        }
    }

    private void isNumbersFromUserInBound(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .filter(userNumber -> userNumber > MAX_NUMBER || userNumber < MIN_NUMBER)
                .toList();
        if (!result.isEmpty()) {
            errorList.add(NUMBERS_OUT_OF_BOUND_MESSAGE);
        }
    }

    private void isNumbersFromUserDistinctive(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .filter(i -> Collections.frequency(numbersFromUser, i) > 1)
                .toList();
        if (!result.isEmpty()) {
            errorList.add(DUPLICATED_NUMBERS_MESSAGE);
        }
    }

    private void isNotLessThanSixNumbersFromUser(List<Integer> numbersFromUser) {
        boolean isCorrect = !(numbersFromUser.size() < MAX_NUMBERS_FROM_USER);
        if (!isCorrect) {
            errorList.add(NOT_ENOUGH_NUMBERS_MESSAGE);
        }
    }

    private void isNotMoreThanSixNumbersFromUser(List<Integer> numbersFromUser) {
        boolean isCorrect = !(numbersFromUser.size() > MAX_NUMBERS_FROM_USER);
        if (!isCorrect) {
            errorList.add(TOO_MANY_NUMBERS_MESSAGE);
        }
    }

    private String joinMessages() {
        String message = "Failed! Fix those issues: ";
        message += errorList.stream().map(ValidationMessage::getMessage).collect(Collectors.joining(","));
        return message;
    }
}
