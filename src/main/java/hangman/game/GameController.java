package hangman.game;

import hangman.factory.GameFactory;
import hangman.io.UserInput;
import hangman.io.console.ConsoleRenderer;


public class GameController {

    private final GameFactory factory;
    private final ConsoleRenderer renderer;
    private final UserInput input;

    public GameController(GameFactory factory, ConsoleRenderer renderer, UserInput input) {
        this.factory = factory;
        this.renderer = renderer;
        this.input = input;
    }

    public void start() {
        Game game = factory.create();
        renderer.printGameStarted();

        // начальный экран
        renderSnapshot(game, true);

        while (game.isInProgress()) {
            processTurn(game);
            renderSnapshot(game, game.isInProgress());
        }

        finishGame(game);
    }

    private void finishGame(Game game) {
        String revealWord = game.revealWord();
        if (game.isWon()) {
            renderer.printWonGame(revealWord);
        } else {
            renderer.printLoseGame(revealWord);
        }
    }

    private void processTurn(Game game) {
        char guessChar = input.readSingleChar();
        GuessResult result = game.guess(guessChar);

        switch (result) {
            case CORRECT -> renderer.printCorrectLetter();
            case INCORRECT -> renderer.printIncorrectLetter();
            case ALREADY_USED -> renderer.printAlreadyUsedLetter();
            case NOT_A_LETTER -> renderer.printOnlyLettersSupported();
            case WRONG_ALPHABET -> renderer.printWrongAlphabet();
        }
    }

    private void renderSnapshot(Game game, boolean askForInput) {
        int errors = game.getErrorCount();
        int attempts = game.getAttemptsLeft();
        WordProgress progress = game.getWordProgress();

        renderer.renderHangman(errors);

        boolean showWordProgress = !game.isLost();
        renderer.renderGameState(errors, attempts, progress, showWordProgress);

        if (askForInput) {
            renderer.printInputPrompt();
        }
    }
}
