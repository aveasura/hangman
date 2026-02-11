package hangman.factory;

import hangman.game.Game;
import hangman.word.WordProvider;

public class RandomTxtWordGameFactory implements GameFactory {

    private final WordProvider provider;

    public RandomTxtWordGameFactory(WordProvider provider) {
        this.provider = provider;
    }

    @Override
    public Game create() {
        String word = provider.getWord();
        return new Game(word);
    }
}
