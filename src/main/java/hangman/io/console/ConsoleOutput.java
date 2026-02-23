package hangman.io.console;

import hangman.io.Output;

public class ConsoleOutput implements Output {
    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
