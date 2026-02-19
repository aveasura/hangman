package hangman.word;

import java.util.Locale;

public class CanonizationWordProvider implements WordProvider {
    private final WordProvider delegate;

    public CanonizationWordProvider(WordProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getWord() {
        return delegate.getWord().trim().toLowerCase(Locale.ROOT);
    }
}
