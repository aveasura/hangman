package hangman.game;

/**
 * Строгий типобезопасный контракт без null:
 *  OpenedSlot содержит букву
 *  HiddenSlot не содержит буквы вообще
 */
public sealed interface Slot permits OpenedSlot, HiddenSlot {
    SlotState state();
}