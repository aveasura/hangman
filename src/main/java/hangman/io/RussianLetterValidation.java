package hangman.io;

public class RussianLetterValidation implements LetterValidation {

    private static final char FIRST_LETTER = 'а';
    private static final char LAST_LETTER = 'я';
    private static final char SPECIAL_LETTER = 'ё';

    @Override
    public boolean isValid(char c) {
        return c == SPECIAL_LETTER || (c >= FIRST_LETTER && c <= LAST_LETTER);
    }
}
