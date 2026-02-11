package hangman.game;

public record OpenedSlot(char letter) implements Slot {
    @Override
    public SlotState state() {
        return SlotState.OPENED;
    }
}
