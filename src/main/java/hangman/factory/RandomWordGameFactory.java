package hangman.factory;

import hangman.io.LetterValidation;
import hangman.game.Game;
import hangman.word.WordProvider;

public class RandomWordGameFactory implements GameFactory {

    private final WordProvider provider;
    private final LetterValidation validation;

    public RandomWordGameFactory(WordProvider provider, LetterValidation validation) {
        this.provider = provider;
        this.validation = validation;
    }

    @Override
    public Game create() {
        String word = provider.getWord();// <- тут может?
        return new Game(word, validation);
    }
}
