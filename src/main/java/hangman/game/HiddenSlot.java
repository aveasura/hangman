package hangman.game;

public record HiddenSlot() implements Slot {
    @Override
    public SlotState state() {
        return SlotState.HIDDEN;
    }
}
