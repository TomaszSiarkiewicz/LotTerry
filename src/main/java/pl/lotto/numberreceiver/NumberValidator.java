package pl.lotto.numberreceiver;

import java.util.Collections;
import java.util.List;

class NumberValidator {
    ValidationConstants constants = new ValidationConstants();
    private String message = "";
    private boolean isValid = true;

    public NumberValidator() {
    }

    public boolean validate(List<Integer> numbersFromUser) {
        isNumbersFromUserInBound(numbersFromUser);
        isNumbersFromUserDistinctive(numbersFromUser);
        isNotLessThanSixNumbersFromUser(numbersFromUser);
        isNotMoreThanSixNumbersFromUser(numbersFromUser);

        if (isValid) {
            message = constants.getCORRECT_MESSAGE();
            return true;
        } else {
            message = "Failed! Fix those issues: \n" + message;
            return false;
        }
    }

    private void isNumbersFromUserInBound(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .filter(givenNumber -> givenNumber > constants.getMAX_NUMBER() || givenNumber < constants.getMIN_NUMBER())
                .toList();
        boolean isCorrect = result.size() == 0;
        if (!isCorrect) {
            message = message.concat(constants.getNUMBERS_OUT_OF_BOUND_MESSAGE());
            isValid = false;
        }
    }

    private void isNumbersFromUserDistinctive(List<Integer> numbersFromUser) {
        List<Integer> result = numbersFromUser.stream()
                .filter(i -> Collections.frequency(numbersFromUser, i) > 1)
                .toList();
        boolean isCorrect = result.size() == 0;
        if (!isCorrect) {
            message = message.concat(constants.getDUPLICATED_NUMBERS_MESSAGE());
            isValid = false;
        }
    }

    private void isNotLessThanSixNumbersFromUser(List<Integer> numbersFromUser) {
        boolean isCorrect = !(numbersFromUser.size() < constants.getMAX_NUMBERS_FROM_USER());
        if (!isCorrect) {
            message = message.concat(constants.getNOT_ENOUGH_NUMBERS_MESSAGE());
            isValid = false;
        }
    }

    private void isNotMoreThanSixNumbersFromUser(List<Integer> numbersFromUser) {
        boolean isCorrect = !(numbersFromUser.size() > constants.getMAX_NUMBERS_FROM_USER());
        if (!isCorrect) {
            message = message.concat(constants.getTOO_MANY_NUMBERS_MESSAGE());
            isValid = false;
        }
    }

    public String getMessage() {
        return message;
    }
}
