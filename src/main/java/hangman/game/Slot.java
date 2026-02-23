package hangman.game;

public final class Slot {
    private static final boolean CLOSED = false;
    private static final boolean OPEN = true;

    private final char letter;
    private final boolean isOpen;

    public Slot(char letter) {
        this(letter, CLOSED);
    }

    private Slot(char letter, boolean isOpen) {
        this.letter = letter;
        this.isOpen = isOpen;
    }

    public Slot open() {
        return isOpen ? this : new Slot(letter, OPEN);
    }

    public char getLetter() {
        return letter;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
