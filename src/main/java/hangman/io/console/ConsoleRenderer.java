package hangman.io.console;

import hangman.game.WordView;
import hangman.io.Output;

import java.util.List;
import java.util.Objects;

public class ConsoleRenderer {

    private static final char HIDDEN_CHAR = '*';

    private static final String MAIN_MENU = """
            1. Начать новую игру
            2. Выход""";

    private static final List<String> HANGMAN_STATES = List.of(
            """
                     +---+
                     |   |
                         |
                         |
                         |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                         |
                         |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                     |   |
                         |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|   |
                         |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                         |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                    /    |
                         |
                    =========
                    """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                    / \\  |
                         |
                    =========
                    """
    );

    private final Output output;

    public ConsoleRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    public void printMainMenu() {
        output.print(MAIN_MENU);
    }

    public void printInputPrompt() {
        output.print("Введите русскую букву");
    }

    public void printExitMessage() {
        output.print("Выход...");
    }

    public void printInvalidMenuChoice() {
        output.print("Выберите цифру из предложенных.");
    }

    public void printGameStarted() {
        output.print("Игра начинается.");
    }

    public void renderTurnState(int errorCount, int attemptsLeft, WordView view, List<Character> usedLetters) {
        printStats(errorCount, attemptsLeft);
        printWordProgress(view);
        printUsedLetters(usedLetters);
    }

    public void printStats(int errorCount, int attemptsLeft) {
        output.print("Ошибок: " + errorCount);
        output.print("Попыток осталось: " + attemptsLeft);
    }

    public void printWordProgress(WordView view) {
        output.print("Текущее состояние слова: " + formatWordCurrentState(view));
    }

    public void printUsedLetters(List<Character> usedLetters) {
        output.print("Список введённых вами букв: " + formatUsedLetters(usedLetters));
    }

    public void printCorrectLetter() {
        output.print("Верно!");
    }

    public void printIncorrectLetter() {
        output.print("Неверно!");
    }

    public void printAlreadyUsedLetter() {
        output.print("Вы уже вводили эту букву. Введите другую.");
    }

    public void printWonGame(WordView view) {
        String word = formatRevealedSecretWord(view);
        output.print("Вы выиграли! Загаданное слово: " + word);
    }

    public void printLoseGame(WordView view) {
        String word = formatRevealedSecretWord(view);
        output.print("Вы проиграли! Загаданное слово: " + word);
    }

    public void printIncorrectInput() {
        output.print("Вы ничего не ввели. Попробуйте снова.");
    }

    public void printOnlyLettersSupported() {
        output.print("Поддерживаются только буквы");
    }

    public void printSingleLetterRequired() {
        output.print("Введите ровно одну русскую букву.");
    }

    public void renderHangman(int errors) {
        int idx = Math.min(errors, HANGMAN_STATES.size() - 1);
        output.print(HANGMAN_STATES.get(idx));
    }

    public void printWrongAlphabet() {
        output.print("Вводите только русские буквы (а-я, ё)");
    }

    private String formatUsedLetters(List<Character> usedLetters) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < usedLetters.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(usedLetters.get(i));
        }

        return sb.toString();
    }

    private String formatWordCurrentState(WordView view) {
        StringBuilder sb = new StringBuilder();

        char[] letters = view.letters();
        boolean[] open = view.open();
        for (int i = 0; i < letters.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }

            char ch = open[i] ? letters[i] : HIDDEN_CHAR;
            sb.append(ch);
        }

        return sb.toString();
    }

    private String formatRevealedSecretWord(WordView view) {
        return new String(view.letters());
    }
}
