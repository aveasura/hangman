package hangman.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Game {
    private static final int MAX_ERRORS = 6;

    private final String word;
    private final Set<Character> usedChars = new HashSet<>();
    private int errorsCount = 0;

    public Game(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Слово не должно быть пустым");
        }

        // Защитная канонизация: trim + lower-case в Game на случай изменений WordProvider.
        this.word = word.trim().toLowerCase(Locale.ROOT);
    }

    public GuessResult guess(char input) {
        if (!isInProgress()) {
            throw new IllegalStateException("Игра уже завершена");
        }

        char c = Character.toLowerCase(input);
        if (!Character.isLetter(c)) {
            throw new IllegalArgumentException("Поддерживаются только буквы");
        }

        if (!acceptsInput(c)) {
            throw new IllegalArgumentException("Поддерживаются только русские буквы (а-я, ё)");
        }

        if (!usedChars.add(c)) {
            return GuessResult.ALREADY_USED;
        }

        if (word.indexOf(c) >= 0) {
            return GuessResult.CORRECT;
        }

        errorsCount++;
        return GuessResult.INCORRECT;
    }

    /**
     * Вне домена скрытые буквы недоступны по типу:
     * HiddenSlot вообще не содержит символ.
     */
    public WordProgress getWordProgress() {
        List<Slot> slots = new ArrayList<>(word.length());
        boolean revealAll = !isInProgress();

        for (char c : word.toCharArray()) {
            boolean opened = revealAll || usedChars.contains(c);
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
            if (!usedChars.contains(c)) {
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
            throw new IllegalStateException("Нельзя раскрыть слово до завершения игры");
        }
        return word;
    }

    public boolean acceptsInput(char c) {
        return isRussianLetter(c);
    }

    private boolean isRussianLetter(char c) {
        return c == 'ё' || (c >= 'а' && c <= 'я');
    }
}
