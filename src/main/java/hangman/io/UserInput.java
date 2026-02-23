package hangman.io;

public interface UserInput extends AutoCloseable {
    String readNonEmptyInput();

    char readSingleChar();

    @Override
    void close();
}
