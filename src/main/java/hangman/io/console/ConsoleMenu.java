package hangman.io.console;

import hangman.game.GameController;
import hangman.io.UserInput;

public class ConsoleMenu {

    private static final String START = "1";
    private static final String EXIT = "2";

    private final UserInput input;
    private final ConsoleRenderer renderer;
    private final GameController controller;

    public ConsoleMenu(UserInput input, ConsoleRenderer renderer, GameController controller) {
        this.input = input;
        this.renderer = renderer;
        this.controller = controller;
    }

    public void start() {
        while (true) {
            renderer.printMainMenu();

            String choice = input.readNonEmptyInput();
            switch (choice) {
                case START -> controller.start();
                case EXIT -> {
                    renderer.printExitMessage();
                    return;
                }
                default -> renderer.printInvalidMenuChoice();
            }
        }
    }
}
