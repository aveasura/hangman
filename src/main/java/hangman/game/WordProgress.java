package hangman.game;

import java.util.List;

public record WordProgress(List<Slot> slots) {
    public WordProgress {
        if (slots == null) {
            throw new IllegalArgumentException("слоты не должны быть null");
        }
        slots = List.copyOf(slots);
    }
}
