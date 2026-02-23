package hangman.word;

import java.util.Locale;
import java.util.Objects;

public class CanonizationWordProvider implements WordProvider {
    private final WordProvider delegate;

    public CanonizationWordProvider(WordProvider delegate) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
    }

    @Override
    public String getWord() {
        return delegate.getWord().trim().toLowerCase(Locale.ROOT);
    }
}
