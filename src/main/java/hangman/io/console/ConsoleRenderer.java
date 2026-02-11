package hangman.io.console;

import hangman.game.HiddenSlot;
import hangman.game.OpenedSlot;
import hangman.game.Slot;
import hangman.game.WordProgress;

import java.util.List;

public class ConsoleRenderer {

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

    public void printMainMenu() {
        print(MAIN_MENU);
    }

    public void printInputPrompt() {
        print("Введите букву");
    }

    public void printExitMessage() {
        print("Выход...");
    }

    public void printInvalidMenuChoice() {
        print("Выберите цифру из предложенных.");
    }

    public void printGameStarted() {
        print("Игра начинается.");
    }

    public void renderGameState(int errorCount, int attemptsLeft, WordProgress progress, boolean showWordProgress) {
        print("Ошибок: " + errorCount);
        print("Попыток осталось: " + attemptsLeft);

        if (showWordProgress) {
            print("Текущее состояние слова: " + format(progress));
        }
    }

    private String format(WordProgress progress) {
        StringBuilder sb = new StringBuilder();

        for (Slot slot : progress.slots()) {
            if (!sb.isEmpty()) {
                sb.append(" ");
            }

            switch (slot) {
                case OpenedSlot opened -> sb.append(opened.letter());
                case HiddenSlot ignored -> sb.append('*');
            }
        }
        return sb.toString();
    }

    public void printCorrectLetter() {
        print("Верно!");
    }

    public void printIncorrectLetter() {
        print("Неверно!");
    }

    public void printAlreadyUsedLetter() {
        print("Вы уже вводили эту букву. Введите другую.");
    }

    public void printGameResult(boolean won, String word) {
        if (won) {
            print("Вы выиграли! Загаданное слово: " + word);
        } else {
            print("Вы проиграли! Загаданное слово: " + word);
        }
    }

    public void printEmptyLine() {
        print("Вы ничего не ввели. Попробуйте снова.");
    }

    public void printSingleLetterRequired() {
        print("Введите ровно одну букву.");
    }

    public void renderHangman(int errors) {
        String result = HANGMAN_STATES.get(errors);
        print(result);
    }

    private void print(String text) {
        System.out.println(text);
    }
}
