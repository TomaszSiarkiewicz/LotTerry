package pl.lotto.numberreceiver;

public enum ValidationMessage {
    CORRECT_MESSAGE("Success!"),
    NUMBERS_OUT_OF_BOUND_MESSAGE("At least one number is incorrect"),
    NOT_ENOUGH_NUMBERS_MESSAGE("Not enough numbers"),
    TOO_MANY_NUMBERS_MESSAGE("Too many numbers"),
    DUPLICATED_NUMBERS_MESSAGE("Numbers are duplicated");

    private final String message;

    ValidationMessage(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}
