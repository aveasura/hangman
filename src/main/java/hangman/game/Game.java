package hangman.game;

import hangman.io.LetterValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Game {
    private static final int MAX_ERRORS = 6;

    private final String word;
    private final Set<Character> usedLetters = new HashSet<>();
    private int errorsCount = 0;

    private final LetterValidation validation;

    public Game(String word, LetterValidation validation) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("The word should not be empty");
        }

        if (validation == null) {
            throw new IllegalArgumentException("Validation must not be null");
        }

        if (!word.equals(word.trim()) || !word.equals(word.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("The word must be canonized (trim + lower-case)");
        }

        this.word = word;
        this.validation = validation;
    }

    public GuessResult estimate(char input) {
        if (!isInProgress()) {
            throw new IllegalStateException("The game is already over");
        }

        char c = Character.toLowerCase(input);
        if (!Character.isLetter(c)) {
            return GuessResult.NOT_A_LETTER;
        }

        if (!validation.isValid(c)) {
            return GuessResult.WRONG_ALPHABET;
        }

        if (usedLetters.contains(c)) {
            return GuessResult.ALREADY_USED;
        }

        if (word.indexOf(c) >= 0) {
            return GuessResult.CORRECT;
        }

        return GuessResult.INCORRECT;
    }

    public void apply(char input) {
        GuessResult result = estimate(input);

        if (result == GuessResult.NOT_A_LETTER || result == GuessResult.WRONG_ALPHABET || result == GuessResult.ALREADY_USED) {
            return;
        }

        char c = Character.toLowerCase(input);
        usedLetters.add(c);

        if (result == GuessResult.INCORRECT) {
            errorsCount++;
        }
    }

    // todo
    /**
     * Вне домена скрытые буквы недоступны по типу:
     * HiddenSlot вообще не содержит символ.
     */
    public WordProgress getWordProgress() {
        List<Slot> slots = new ArrayList<>(word.length());
        boolean revealAll = !isInProgress();

        for (char c : word.toCharArray()) {
            boolean opened = revealAll || usedLetters.contains(c);
            slots.add(opened ? new OpenedSlot(c) : new HiddenSlot());
        }

        return new WordProgress(slots);
    }

    public int getErrorCount() {
        return errorsCount;
    }

    public boolean isInProgress() {
        return !isWon() && !isLost();
    }

    public boolean isWon() {
        for (char c : word.toCharArray()) {
            if (!usedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean isLost() {
        return errorsCount >= MAX_ERRORS;
    }

    public int getAttemptsLeft() {
        return Math.max(0, MAX_ERRORS - errorsCount);
    }

    public String revealWord() {
        if (isInProgress()) {
            throw new IllegalStateException("The word cannot be revealed until the game is completed.");
        }
        return word;
    }
}
