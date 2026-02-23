package hangman.app;

import hangman.factory.GameFactory;
import hangman.game.Game;
import hangman.game.GuessResult;
import hangman.game.WordView;
import hangman.io.UserInput;
import hangman.io.console.ConsoleRenderer;

import java.util.List;
import java.util.Objects;


public class GameController {

    private final GameFactory<Game> factory;
    private final ConsoleRenderer renderer;
    private final UserInput input;

    public GameController(GameFactory<Game> factory, ConsoleRenderer renderer, UserInput input) {
        this.factory = Objects.requireNonNull(factory, "factory must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
        this.input = Objects.requireNonNull(input, "input must not be null");
    }

    public void start() {
        Game game = factory.create();
        renderer.printGameStarted();

        // начальный экран
        renderSnapshot(game);

        while (game.isInProgress()) {
            processTurn(game);
            renderSnapshot(game);
        }

        finishGame(game);
    }

    private void renderSnapshot(Game game) {
        int errors = game.getErrorCount();
        int attempts = game.getAttemptsLeft();
        renderer.renderHangman(errors);

        boolean inProgress = game.isInProgress();
        if (!inProgress) {
            renderer.printStats(errors, attempts);
            return;
        }

        WordView progress = game.getWordProgress();
        List<Character> usedLetters = game.getUsedLetters();
        renderer.renderTurnState(errors, attempts, progress, usedLetters);
    }

    private void processTurn(Game game) {
        renderer.printInputPrompt();
        char guessChar = input.readSingleChar();
        GuessResult result = game.estimate(guessChar);
        game.apply(guessChar, result);

        switch (result) {
            case CORRECT -> renderer.printCorrectLetter();
            case INCORRECT -> renderer.printIncorrectLetter();
            case ALREADY_USED -> renderer.printAlreadyUsedLetter();
            case NOT_A_LETTER -> renderer.printOnlyLettersSupported();
            case WRONG_ALPHABET -> renderer.printWrongAlphabet();
        }
    }

    private void finishGame(Game game) {
        WordView revealWord = game.revealWord();
        if (game.isWon()) {
            renderer.printWonGame(revealWord);
        } else {
            renderer.printLoseGame(revealWord);
        }
    }
}
