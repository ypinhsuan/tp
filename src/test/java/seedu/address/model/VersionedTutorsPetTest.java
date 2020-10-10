package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudent.AMY;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import seedu.address.model.exception.RedoStateException;
import seedu.address.model.exception.UndoStateException;

public class VersionedTutorsPetTest {

    public static final String COMMIT_MESSAGE_1 = "Commit Message 1";
    public static final String COMMIT_MESSAGE_2 = "Commit Message 2";
    public static final String COMMIT_MESSAGE_3 = "Commit Message 3";

    private VersionedTutorsPet versionedTutorsPet = new VersionedTutorsPet(getTypicalTutorsPet());

    @Test
    public void commit_nullCommitMessage_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> versionedTutorsPet.commit(null));
    }

    @Test
    public void commit_commit_removesFutureStates() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.undo();
        assertTrue(versionedTutorsPet.canRedo());
        versionedTutorsPet.commit("State 2");
        assertFalse(versionedTutorsPet.canRedo());
    }

    @Test
    public void canUndo_hasPreviousState_returnsTrue() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);

        assertTrue(versionedTutorsPet.canUndo());
    }

    @Test
    public void canUndo_newVersionedTutorsPet_returnsFalse() {
        assertFalse(versionedTutorsPet.canUndo());
    }

    @Test
    public void undo_hasPreviousState_returnsCommitMessage() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        assertEquals(COMMIT_MESSAGE_1, versionedTutorsPet.undo());
    }

    @Test
    public void undo_preservesFutureState() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.undo();
        assertTrue(versionedTutorsPet.canRedo());
    }

    @Test
    public void undo_initialState_throwsUndoStateException() {
        assertThrows(UndoStateException.class, () -> versionedTutorsPet.undo());
    }

    @Test
    public void canRedo_hasNextState_returnsTrue() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.undo();
        assertTrue(versionedTutorsPet.canRedo());
    }

    @Test
    public void canRedo_noNextState_returnsFalse() {
        assertFalse(versionedTutorsPet.canRedo());
    }

    @Test
    public void redo_hasNextState_returnsCommitMessage() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.undo();
        assertEquals(COMMIT_MESSAGE_1, versionedTutorsPet.redo());
    }

    @Test
    public void redo_preservesRedoneState() {
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.commit(COMMIT_MESSAGE_2);
        assertEquals(COMMIT_MESSAGE_2, versionedTutorsPet.undo());
        assertEquals(COMMIT_MESSAGE_2, versionedTutorsPet.redo());
        assertTrue(versionedTutorsPet.canUndo());
        assertEquals(COMMIT_MESSAGE_2, versionedTutorsPet.undo());
    }

    @Test
    public void redo_noNextState_throwsRedoStateException() {
        assertThrows(RedoStateException.class, () -> versionedTutorsPet.redo());
    }

    @Test
    public void equals() {
        // same state -> returns true
        VersionedTutorsPet versionedTutorsPetCopy = new VersionedTutorsPet(getTypicalTutorsPet());
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPet.undo();
        versionedTutorsPetCopy.commit(COMMIT_MESSAGE_1);
        versionedTutorsPetCopy.undo();
        assertTrue(versionedTutorsPet.equals(versionedTutorsPetCopy));

        // same object -> returns true
        assertTrue(versionedTutorsPet.equals(versionedTutorsPet));

        // null -> returns false
        assertFalse(versionedTutorsPet.equals(null));

        // different type -> returns false
        assertFalse(versionedTutorsPet.equals(5));

        // different state pointer -> returns false
        versionedTutorsPetCopy.redo();
        assertFalse(versionedTutorsPet.equals(versionedTutorsPetCopy));

        // different state history -> returns false
        versionedTutorsPet = new VersionedTutorsPet(getTypicalTutorsPet());
        VersionedTutorsPet versionedTutorsPetDifferentState = new VersionedTutorsPet(getTypicalTutorsPet());
        versionedTutorsPet.commit(COMMIT_MESSAGE_1);
        versionedTutorsPetDifferentState.commit(COMMIT_MESSAGE_2);
        assertFalse(versionedTutorsPet.equals(versionedTutorsPetDifferentState));

        // different current state -> returns false
        versionedTutorsPet = new VersionedTutorsPet(getTypicalTutorsPet());
        VersionedTutorsPet versionedTutorsPetDifferentCurrentState = new VersionedTutorsPet(getTypicalTutorsPet());
        versionedTutorsPetDifferentCurrentState.addStudent(AMY);
        assertFalse(versionedTutorsPet.equals(versionedTutorsPetDifferentCurrentState));
    }
}
