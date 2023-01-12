package pl.lotto.numberreceiver;

class ValidationConstants {
    private final int MAX_NUMBERS_FROM_USER = 6;
    private final int MAX_NUMBER = 99;
    private final int MIN_NUMBER = 1;
    private final String CORRECT_MESSAGE = "Success!";
    private final String NUMBERS_OUT_OF_BOUND_MESSAGE = "At least one number is incorrect \n";
    private final String NOT_ENOUGH_NUMBERS_MESSAGE = "Not enough numbers \n";
    private final String TOO_MANY_NUMBERS_MESSAGE = "Too many numbers \n";
    private final String DUPLICATED_NUMBERS_MESSAGE = "Numbers are duplicated \n";

    public ValidationConstants() {
    }

    public int getMAX_NUMBERS_FROM_USER() {
        return MAX_NUMBERS_FROM_USER;
    }

    public int getMAX_NUMBER() {
        return MAX_NUMBER;
    }

    public int getMIN_NUMBER() {
        return MIN_NUMBER;
    }

    public String getCORRECT_MESSAGE() {
        return CORRECT_MESSAGE;
    }

    public String getNUMBERS_OUT_OF_BOUND_MESSAGE() {
        return NUMBERS_OUT_OF_BOUND_MESSAGE;
    }

    public String getNOT_ENOUGH_NUMBERS_MESSAGE() {
        return NOT_ENOUGH_NUMBERS_MESSAGE;
    }

    public String getTOO_MANY_NUMBERS_MESSAGE() {
        return TOO_MANY_NUMBERS_MESSAGE;
    }

    public String getDUPLICATED_NUMBERS_MESSAGE() {
        return DUPLICATED_NUMBERS_MESSAGE;
    }
}
