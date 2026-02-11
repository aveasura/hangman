package hangman.io.console;

import hangman.io.InputProvider;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String nextLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Ввод недоступен: поток stdin закрыт");
        }
        return scanner.nextLine().trim();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
