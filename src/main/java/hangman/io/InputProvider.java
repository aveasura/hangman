package hangman.io;

public interface InputProvider extends AutoCloseable {

    String nextLine();

    @Override
    void close();
}
