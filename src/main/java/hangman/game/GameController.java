package hangman.game;

import hangman.factory.GameFactory;
import hangman.io.InputProvider;
import hangman.io.console.ConsoleRenderer;


public class GameController {

    private final GameFactory factory;
    private final ConsoleRenderer renderer;
    private final InputProvider provider;

    public GameController(GameFactory factory, ConsoleRenderer renderer, InputProvider provider) {
        this.factory = factory;
        this.renderer = renderer;
        this.provider = provider;
    }

    public void playGame() {
        Game game = factory.create();
        renderer.printGameStarted();

        // начальный экран
        renderSnapshot(game, true);

        while (game.isInProgress()) {
            processTurn();
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

    public void exit() {
        renderer.printExitMessage();
    }


    private void processTurn() {
        char input = provider.readSingleLetter();
        GuessResult result = game.guess(input);

        switch (result) {
            case CORRECT -> renderer.printCorrectLetter();
            case INCORRECT -> renderer.printIncorrectLetter();
            case ALREADY_USED -> renderer.printAlreadyUsedLetter();
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
