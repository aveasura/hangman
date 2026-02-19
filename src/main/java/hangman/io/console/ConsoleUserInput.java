package hangman.io.console;

import hangman.io.InputProvider;
import hangman.io.LetterValidation;
import hangman.io.UserInput;

public class ConsoleUserInput implements UserInput {

    private final ConsoleRenderer renderer;
    private final InputProvider input;
    private final LetterValidation validation;

    public ConsoleUserInput(ConsoleRenderer renderer, InputProvider input, LetterValidation validation) {
        this.renderer = renderer;
        this.input = input;
        this.validation = validation;
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
                renderer.printSingleLetterRequired();
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
