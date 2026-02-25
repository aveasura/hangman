package hangman.factory;

import hangman.game.WordProgress;
import hangman.validation.LetterValidation;
import hangman.game.Game;
import hangman.word.WordProvider;

import java.util.Objects;

public class RandomWordGameFactory implements Factory<Game> {

    private final WordProvider provider;
    private final LetterValidation validation;

    public RandomWordGameFactory(WordProvider provider, LetterValidation validation) {
        this.provider = Objects.requireNonNull(provider, "provider must not be null");
        this.validation = Objects.requireNonNull(validation, "validation must not be null");
    }

    @Override
    public Game create() {
        String word = provider.getWord();
        WordProgress wordProgress = new WordProgress(word);
        return new Game(wordProgress, validation);
    }
}
