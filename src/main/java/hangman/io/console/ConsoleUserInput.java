package hangman.io.console;

import hangman.io.InputProvider;
import hangman.validation.LetterValidation;
import hangman.io.UserInput;

import java.util.Objects;

public class ConsoleUserInput implements UserInput {

    private final ConsoleRenderer renderer;
    private final InputProvider input;
    private final LetterValidation validation;

    public ConsoleUserInput(ConsoleRenderer renderer, InputProvider input, LetterValidation validation) {
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
        this.input = Objects.requireNonNull(input, "input must not be null");
        this.validation = Objects.requireNonNull(validation, "validation must not be null");
    }

    @Override
    public String readNonEmptyInput() {
        while (true) {
            String line = input.nextLine();
            if (!line.isEmpty()) {
                return line;
            }
            renderer.printIncorrectInput();
        }
    }

    @Override
    public char readSingleChar() {
        while (true) {
            String line = input.nextLine();
            if (line.isEmpty()) {
                renderer.printIncorrectInput();
                continue;
            }

            if (line.length() != 1) {
                renderer.printSingleLetterRequired();
                continue;
            }

            char c = Character.toLowerCase(line.charAt(0));
            if (!Character.isLetter(c)) {
                renderer.printOnlyLettersSupported();
                continue;
            }

            if (!validation.isValid(c)) {
                renderer.printWrongAlphabet();
                continue;
            }

            return c;
        }
    }

    @Override
    public void close() {
        input.close();
    }
}
