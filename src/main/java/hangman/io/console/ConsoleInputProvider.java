package hangman.io.console;

import hangman.game.Game;
import hangman.io.InputProvider;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner = new Scanner(System.in);
    private final ConsoleRenderer renderer;

    public ConsoleInputProvider(ConsoleRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public String nextLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input not available: stdin stream closed");
        }
        return scanner.nextLine().trim();
    }

    @Override
    public void close() {
        scanner.close();
    }

    public String readNonEmptyInput() {
        while (true) {
            String line = nextLine();
            if (!line.isEmpty()) {
                return line;
            }
            renderer.printIncorrectInput();
        }
    }

    public char readSingleRussianLetter() {
        while (true) {
            String line = nextLine();

            if (line.isEmpty()) {
                renderer.printIncorrectInput();
                continue;
            }

            if (line.length() != 1) {
                renderer.printSingleLetterRequired();
                continue;
            }

            char c = Character.toLowerCase(line.charAt(0));

            if (!Character.isLetter(c)) {
                renderer.printSingleLetterRequired();
                continue;
            }

            if (isRussianLetter(c)) {
                return c;

            }

            renderer.printWrongAlphabet();
        }
    }

    private boolean isRussianLetter(char c) {
        return c == 'ё' || (c >= 'а' && c <= 'я');
    }
}
