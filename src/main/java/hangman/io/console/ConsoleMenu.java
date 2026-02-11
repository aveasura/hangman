package hangman.io.console;

import hangman.factory.GameFactory;
import hangman.game.Game;
import hangman.game.GuessResult;
import hangman.game.WordProgress;
import hangman.io.InputProvider;

public class ConsoleMenu {

    private static final String START = "1";
    private static final String EXIT = "2";

    private final GameFactory factory;
    private final InputProvider provider;
    private final ConsoleRenderer renderer;

    public ConsoleMenu(GameFactory factory, InputProvider provider, ConsoleRenderer renderer) {
        this.factory = factory;
        this.provider = provider;
        this.renderer = renderer;
    }

    public void start() {
        while (true) {
            renderer.printMainMenu();
            String input = readNonEmptyInput();

            switch (input) {
                case START -> playGame();
                case EXIT -> {
                    renderer.printExitMessage();
                    return;
                }
                default -> renderer.printInvalidMenuChoice();
            }
        }
    }

    private void playGame() {
        Game game = factory.create();
        renderer.printGameStarted();

        // начальный экран
        renderSnapshot(game, true);

        while (game.isInProgress()) {
            processTurn(game);
            renderSnapshot(game, game.isInProgress());
        }

        renderer.printGameResult(game.isWon(), game.revealWord());
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

    private void processTurn(Game game) {
        char input = readSingleLetter();
        GuessResult result = game.guess(input);

        switch (result) {
            case CORRECT -> renderer.printCorrectLetter();
            case INCORRECT -> renderer.printIncorrectLetter();
            case ALREADY_USED -> renderer.printAlreadyUsedLetter();
        }
    }

    private String readNonEmptyInput() {
        while (true) {
            String line = input();
            if (!line.isEmpty()) {
                return line;
            }
            renderer.printEmptyLine();
        }
    }

    private char readSingleLetter() {
        while (true) {
            String line = input();

            if (line.isEmpty()) {
                renderer.printEmptyLine();
                continue;
            }

            if (line.length() != 1 || !Character.isLetter(line.charAt(0))) {
                renderer.printSingleLetterRequired();
                continue;
            }

            return line.charAt(0);
        }
    }

    private String input() {
        return provider.nextLine();
    }
}
