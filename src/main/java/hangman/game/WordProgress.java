package hangman.game;

import java.util.List;

// todo
public class WordProgress {
    private final List<Slot> slots;

    public WordProgress(List<Slot> slots) {
        this.slots = List.copyOf(slots);
    }


}
