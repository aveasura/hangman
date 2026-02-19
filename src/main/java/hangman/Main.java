package hangman;

import hangman.factory.GameFactory;
import hangman.factory.RandomWordGameFactory;
import hangman.game.GameController;
import hangman.io.InputProvider;
import hangman.io.LetterValidation;
import hangman.io.RussianLetterValidation;
import hangman.io.UserInput;
import hangman.io.console.ConsoleInputProvider;
import hangman.io.console.ConsoleMenu;
import hangman.io.console.ConsoleRenderer;
import hangman.io.console.ConsoleUserInput;
import hangman.word.CanonizationWordProvider;
import hangman.word.FileWordProvider;
import hangman.word.WordProvider;

public class Main {

    public static void main(String[] args) {
        // Пока - data.txt. Можно через args
        WordProvider words = new FileWordProvider("data.txt");
        CanonizationWordProvider canonization = new CanonizationWordProvider(words);
        LetterValidation validation = new RussianLetterValidation();
        GameFactory factory = new RandomWordGameFactory(canonization, validation);
        ConsoleRenderer renderer = new ConsoleRenderer();

        try (InputProvider provider = new ConsoleInputProvider();
             UserInput input = new ConsoleUserInput(renderer, provider, validation)) {

            GameController controller = new GameController(factory, renderer, input);
            ConsoleMenu menu = new ConsoleMenu(input, renderer, controller);

            menu.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
