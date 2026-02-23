package hangman.app;

import hangman.factory.GameFactory;
import hangman.factory.RandomWordGameFactory;
import hangman.game.Game;
import hangman.io.InputProvider;
import hangman.validation.LetterValidation;
import hangman.io.Output;
import hangman.validation.RussianLetterValidation;
import hangman.io.UserInput;
import hangman.io.console.ConsoleInputProvider;
import hangman.io.console.ConsoleMenu;
import hangman.io.console.ConsoleOutput;
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
        GameFactory<Game> factory = new RandomWordGameFactory(canonization, validation);
        Output output = new ConsoleOutput();
        ConsoleRenderer renderer = new ConsoleRenderer(output);

        try (InputProvider provider = new ConsoleInputProvider();
             UserInput input = new ConsoleUserInput(renderer, provider, validation)) {

            GameController controller = new GameController(factory, renderer, input);
            ConsoleMenu menu = new ConsoleMenu(input, renderer, controller);

            menu.start();
        } catch (IllegalStateException e) {
            System.err.printf("Game launch error: %s", e.getMessage());
        }
    }
}
