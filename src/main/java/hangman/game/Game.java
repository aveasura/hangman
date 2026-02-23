package hangman.game;

import hangman.validation.LetterValidation;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Game {
    private static final int MAX_ERRORS = 6;

    private final Set<Character> usedLetters = new HashSet<>();
    private int errorsCount = 0;

    private final WordProgress progress;
    private final LetterValidation validation;

    public Game(WordProgress progress, LetterValidation validation) {
        this.progress = Objects.requireNonNull(progress, "progress must not be null");
        this.validation = Objects.requireNonNull(validation, "validation must not be null");
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

        if (progress.contains(c)) {
            return GuessResult.CORRECT;
        }

        return GuessResult.INCORRECT;
    }

    public void apply(char input, GuessResult result) {
        if (result == null) {
            throw new IllegalArgumentException("GuessResult must not be null");
        }

        if (result != GuessResult.CORRECT && result != GuessResult.INCORRECT) {
            return;
        }

        char c = Character.toLowerCase(input);
        usedLetters.add(c);
        boolean hit = progress.reveal(c);

        if (!hit) {
            errorsCount++;
        }
    }

    public int getErrorCount() {
        return errorsCount;
    }

    public boolean isInProgress() {
        return !isWon() && !isLost();
    }

    public WordView getWordProgress() {
        return progress.view();
    }

    public boolean isWon() {
        return progress.isSolved();
    }

    public boolean isLost() {
        return errorsCount >= MAX_ERRORS;
    }

    public int getAttemptsLeft() {
        return Math.max(0, MAX_ERRORS - errorsCount);
    }

    public List<Character> getUsedLetters() {
        return usedLetters.stream().sorted().toList();
    }

    public WordView revealWord() {
        if (isInProgress()) {
            throw new IllegalStateException("The word cannot be revealed until the game is completed.");
        }
        return progress.view();
    }
}
