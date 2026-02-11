package hangman;

import hangman.factory.GameFactory;
import hangman.factory.RandomTxtWordGameFactory;
import hangman.io.console.ConsoleInputProvider;
import hangman.io.console.ConsoleMenu;
import hangman.io.console.ConsoleRenderer;
import hangman.io.InputProvider;
import hangman.word.TxtWordProvider;
import hangman.word.WordProvider;

public class Main {

    public static void main(String[] args) {
        // Пока - data.txt. Можно через args
        WordProvider words = new TxtWordProvider("data.txt");
        GameFactory factory = new RandomTxtWordGameFactory(words);
        ConsoleRenderer renderer = new ConsoleRenderer();

        try (InputProvider provider = new ConsoleInputProvider()) {
            ConsoleMenu app = new ConsoleMenu(factory, provider, renderer);
            app.start();
        }
    }
}
