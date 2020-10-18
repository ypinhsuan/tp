package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ATTENDANCE_33;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ATTENDANCE_80;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;

public class EditAttendanceDescriptorTest {

    @Test
    public void equals() {
        // same value -> returns true
        EditAttendanceDescriptor descriptorWithSameValues = new EditAttendanceDescriptor(DESC_ATTENDANCE_33);
        assertTrue(DESC_ATTENDANCE_33.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ATTENDANCE_33.equals(DESC_ATTENDANCE_33));

        // null -> returns false
        assertFalse(DESC_ATTENDANCE_33.equals(null));

        // different types -> returns false
        assertFalse(DESC_ATTENDANCE_33.equals(3));

        // different values -> returns false
        assertFalse(DESC_ATTENDANCE_33.equals(DESC_ATTENDANCE_80));
    }
}