package hangman;

import hangman.factory.GameFactory;
import hangman.factory.RandomTxtWordGameFactory;
import hangman.game.GameController;
import hangman.io.InputProvider;
import hangman.io.console.ConsoleInputProvider;
import hangman.io.console.ConsoleMenu;
import hangman.io.console.ConsoleRenderer;
import hangman.word.FileWordProvider;
import hangman.word.WordProvider;

public class Main {

    public static void main(String[] args) {
        // Пока - data.txt. Можно через args
        WordProvider words = new FileWordProvider("data.txt");
        GameFactory factory = new RandomTxtWordGameFactory(words);
        ConsoleRenderer renderer = new ConsoleRenderer();
        GameController controller = new GameController(factory, renderer);

        try (InputProvider provider = new ConsoleInputProvider(renderer)) {
            ConsoleMenu menu = new ConsoleMenu(provider, renderer, controller);
            menu.start();
        }
    }
}
