package hangman.io.console;

import hangman.game.GameController;
import hangman.io.InputProvider;

public class ConsoleMenu {

    private static final String START = "1";
    private static final String EXIT = "2";

    private final InputProvider provider;
    private final ConsoleRenderer renderer;
    private final GameController controller;

    public ConsoleMenu(InputProvider provider, ConsoleRenderer renderer, GameController controller) {
        this.provider = provider;
        this.renderer = renderer;
        this.controller = controller;
    }

    public void start() {
        while (true) {
            renderer.printMainMenu();
            String input = provider.readNonEmptyInput();

            switch (input) {
                case START -> controller.playGame();
                case EXIT -> controller.exit();
                default -> renderer.printInvalidMenuChoice();
            }
        }
    }
}
