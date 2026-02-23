package hangman.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class WordProgress {
    private final List<Slot> slots;

    public WordProgress(String word) {
        word = Objects.requireNonNull(word, "word must not be null");

        if (word.isBlank()) {
            throw new IllegalArgumentException("The word should not be empty");
        }

        if (!word.equals(word.trim()) || !word.equals(word.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("The word must be canonized (trim + lower-case)");
        }

        this.slots = new ArrayList<>(word.length());
        for (char c : word.toCharArray()) {
            Slot slot = new Slot(c);
            slots.add(slot);
        }
    }

    public boolean contains(char guess) {
        for (Slot slot : slots) {
            if (slot.getLetter() == guess) {
                return true;
            }
        }
        return false;
    }

    public boolean reveal(char guess) {
        boolean hit = false;

        for (int i = 0; i < slots.size(); i++) {
            Slot current = slots.get(i);

            if (current.getLetter() == guess) {
                Slot opened = current.open();
                if (opened != current) {
                    hit = true;
                }
                slots.set(i, opened);
            }
        }

        return hit;
    }

    public boolean isSolved() {
        for (Slot slot : slots) {
            if (!slot.isOpen()) {
                return false;
            }
        }
        return true;
    }

    public WordView view() {
        char[] letters = new char[slots.size()];
        boolean[] open = new boolean[slots.size()];
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            letters[i] = slot.getLetter();
            open[i] = slot.isOpen();
        }

        return new WordView(letters, open);
    }
}
