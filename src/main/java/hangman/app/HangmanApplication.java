package hangman.app;

import hangman.factory.Factory;
import hangman.factory.RandomWordGameFactory;
import hangman.game.Game;
import hangman.io.InputProvider;
import hangman.io.Output;
import hangman.io.UserInput;
import hangman.io.console.ConsoleInputProvider;
import hangman.io.console.ConsoleMenu;
import hangman.io.console.ConsoleOutput;
import hangman.io.console.ConsoleRenderer;
import hangman.io.console.ConsoleUserInput;
import hangman.validation.LetterValidation;
import hangman.validation.RussianLetterValidation;
import hangman.word.CanonizationWordProvider;
import hangman.word.FileWordProvider;
import hangman.word.WordProvider;

import java.util.Objects;

public class HangmanApplication implements AutoCloseable {

    private final ConsoleMenu menu;
    private final UserInput input;

    public HangmanApplication(String wordsResource) {
        String resource = Objects.requireNonNull(wordsResource, "wordsResource must not be null");

        Output output = new ConsoleOutput();
        ConsoleRenderer renderer = new ConsoleRenderer(output);

        LetterValidation validation = new RussianLetterValidation();

        InputProvider provider = new ConsoleInputProvider();
        this.input = new ConsoleUserInput(renderer, provider, validation);

        this.menu = createMenu(resource, renderer, input, validation);
    }

    private static ConsoleMenu createMenu(String wordsResource, ConsoleRenderer renderer, UserInput input, LetterValidation validation) {
        WordProvider words = new FileWordProvider(wordsResource);
        WordProvider canonization = new CanonizationWordProvider(words);
        Factory<Game> factory = new RandomWordGameFactory(canonization, validation);
        GameController controller = new GameController(factory, renderer, input);

        return new ConsoleMenu(input, renderer, controller);
    }

    public void start() {
        menu.start();
    }

    @Override
    public void close() {
        input.close();
    }
}
