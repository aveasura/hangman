package hangman.io;

public interface InputProvider extends AutoCloseable {

    String nextLine();

    String readNonEmptyInput();

    char readSingleLetter();

    char readSingleRussianLetter();

    @Override
    void close();
}
