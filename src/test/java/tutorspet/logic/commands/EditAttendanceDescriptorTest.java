package tutorspet.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.logic.commands.CommandTestUtil.DESC_ATTENDANCE_33;

import org.junit.jupiter.api.Test;

import tutorspet.testutil.EditAttendanceDescriptorBuilder;

public class EditAttendanceDescriptorTest {

    @Test
    public void equals() {
        // same value -> returns true
        EditAttendanceCommand.EditAttendanceDescriptor descriptorWithSameValues =
                new EditAttendanceCommand.EditAttendanceDescriptor(DESC_ATTENDANCE_33);
        assertTrue(DESC_ATTENDANCE_33.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ATTENDANCE_33.equals(DESC_ATTENDANCE_33));

        // null -> returns false
        assertFalse(DESC_ATTENDANCE_33.equals(null));

        // different types -> returns false
        assertFalse(DESC_ATTENDANCE_33.equals(3));

        // different values -> returns false
        EditAttendanceCommand.EditAttendanceDescriptor editedAttendance =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).withParticipationScore(80).build();
        assertFalse(DESC_ATTENDANCE_33.equals(editedAttendance));
    }
}
