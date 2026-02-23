package hangman.io.console;

import hangman.app.GameController;
import hangman.io.UserInput;

import java.util.Objects;

public class ConsoleMenu {

    private static final String START = "1";
    private static final String EXIT = "2";

    private final UserInput input;
    private final ConsoleRenderer renderer;
    private final GameController controller;

    public ConsoleMenu(UserInput input, ConsoleRenderer renderer, GameController controller) {
        this.input = Objects.requireNonNull(input, "input must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
        this.controller = Objects.requireNonNull(controller, "controller must not be null");
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
