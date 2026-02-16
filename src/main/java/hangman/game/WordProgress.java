package hangman.game;

import java.util.List;

public record WordProgress(List<Slot> slots) {
    public WordProgress {
        if (slots == null) {
            throw new IllegalArgumentException("slots must not be null");
        }
        slots = List.copyOf(slots);
    }
}
