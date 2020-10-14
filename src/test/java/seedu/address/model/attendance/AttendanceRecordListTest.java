package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_33;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_51;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_80;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_7;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_ALICE_80;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_EMPTY;
import static seedu.address.testutil.TypicalAttendanceRecord.getTypicalAttendanceRecord;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BENSON;
import static seedu.address.testutil.TypicalStudent.CARL;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.address.model.attendance.exceptions.InvalidWeekException;
import seedu.address.model.lesson.NumberOfOccurrences;

public class AttendanceRecordListTest {

    private static final NumberOfOccurrences numberOfOccurrences = new NumberOfOccurrences(
            VALID_NUMBER_OF_OCCURRENCES_7);
    private static final AttendanceRecordList recordList = new AttendanceRecordList(
            numberOfOccurrences.getNumberOfOccurrences());
    private static final Week VALID_WEEK = new Week(1);
    private static final Attendance VALID_ATTENDANCE = new Attendance(10);

    @Test
    public void constructor_emptyAttendanceRecordMap() {
        assertTrue(recordList.getAttendanceRecordList().size() == 7);
        for (AttendanceRecord record: recordList.getAttendanceRecordList()) {
            assertTrue(record.equals(RECORD_EMPTY));
        }
    }

    @Test
    public void getAttendance_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.getAttendance(null, VALID_WEEK));
    }

    @Test
    public void getAttendance_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.getAttendance(ALICE, null));
    }

    @Test
    public void getAttendance_invalidWeek_throwsInvalidWeekException() {
        assertThrows(InvalidWeekException.class, () -> recordList.getAttendance(
                ALICE, new Week(VALID_NUMBER_OF_OCCURRENCES_7 + 1)));
    }

    @Test
    public void getAttendance_nonExistingStudent_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> recordList.getAttendance(ALICE, VALID_WEEK));
    }

    @Test
    public void getAttendance_existingStudent_success() {
        AttendanceRecordList attendanceRecordList = createAliceRecordList();
        Attendance attendance = attendanceRecordList.getAttendance(ALICE, new Week(1));
        assertEquals(new Attendance(VALID_ATTENDANCE_80), attendance);
    }

    @Test
    public void addAttendance_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.addAttendance(
                null, VALID_WEEK, VALID_ATTENDANCE));
    }

    @Test
    public void addAttendance_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.addAttendance(
                ALICE, null, VALID_ATTENDANCE));
    }

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.addAttendance(
                ALICE, VALID_WEEK, null));
    }

    @Test
    public void addAttendance_invalidWeek_throwsInvalidWeekException() {
        assertThrows(InvalidWeekException.class, () -> recordList.addAttendance(
                ALICE, new Week(VALID_NUMBER_OF_OCCURRENCES_7 + 1), VALID_ATTENDANCE));
    }

    @Test
    public void addAttendance_duplicateStudent_throwsDuplicateAttendanceException() {
        AttendanceRecordList attendanceRecordList = createAliceRecordList();
        assertThrows(DuplicateAttendanceException.class, () -> attendanceRecordList.addAttendance(
                ALICE, VALID_WEEK, VALID_ATTENDANCE));
    }

    @Test
    public void addAttendance_nonExistingStudent_success() {
        AttendanceRecordList actualList = recordList
                .addAttendance(ALICE, VALID_WEEK, new Attendance(VALID_ATTENDANCE_80));
        AttendanceRecordList expectedList = createAliceRecordList();
        assertEquals(expectedList.getAttendanceRecordList(), actualList.getAttendanceRecordList());

        // check immutability
        assertNotEquals(recordList.getAttendanceRecordList(), actualList.getAttendanceRecordList());
    }

    @Test
    public void editAttendance_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.editAttendance(
                null, VALID_WEEK, VALID_ATTENDANCE));
    }

    @Test
    public void editAttendance_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.editAttendance(
                ALICE, null, VALID_ATTENDANCE));
    }

    @Test
    public void editAttendance_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.editAttendance(
                ALICE, VALID_WEEK, null));
    }

    @Test
    public void editAttendance_nonExistingStudent_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> recordList.editAttendance(
                ALICE, VALID_WEEK, VALID_ATTENDANCE));
    }

    @Test
    public void editAttendance_existingStudent_success() {
        AttendanceRecordList actualList = recordList
                .addAttendance(ALICE, VALID_WEEK, VALID_ATTENDANCE)
                .editAttendance(ALICE, VALID_WEEK, new Attendance(VALID_ATTENDANCE_80));

        AttendanceRecordList expectedList = createAliceRecordList();
        assertEquals(expectedList.getAttendanceRecordList(), actualList.getAttendanceRecordList());

        // check immutability
        assertNotEquals(recordList.getAttendanceRecordList(), actualList.getAttendanceRecordList());
    }

    @Test
    public void deleteAttendance_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.deleteAttendance(null, VALID_WEEK));
    }

    @Test
    public void deleteAttendance_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.deleteAttendance(ALICE, null));
    }

    @Test
    public void deleteAttendance_invalidWeek_throwsInvalidWeekException() {
        assertThrows(InvalidWeekException.class, () -> recordList.deleteAttendance(
                ALICE, new Week(VALID_NUMBER_OF_OCCURRENCES_7 + 1)));
    }

    @Test
    public void deleteAttendance_nonExistingStudent_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> recordList.deleteAttendance(ALICE, VALID_WEEK));
    }

    @Test
    public void deleteAttendance_existingStudent_student() {
        AttendanceRecordList originalList = createAliceRecordList();
        AttendanceRecordList attendanceRecordList = originalList.deleteAttendance(ALICE, VALID_WEEK);
        assertEquals(recordList.getAttendanceRecordList(), attendanceRecordList.getAttendanceRecordList());

        // check immutability
        assertNotEquals(attendanceRecordList.getAttendanceRecordList(), originalList.getAttendanceRecordList());
    }

    @Test
    public void equals() {
        AttendanceRecordList attendanceRecordList = new AttendanceRecordList(getTypicalAttendanceRecord());

        // same object -> returns true
        assertTrue(attendanceRecordList.equals(attendanceRecordList));
        assertTrue(recordList.equals(recordList));

        // null -> returns false
        assertFalse(attendanceRecordList.equals(null));
        assertFalse(recordList.equals(null));

        // different types -> return false
        assertFalse(attendanceRecordList.equals(attendanceRecordList.getAttendanceRecordList()));

        // same records, same size -> return true
        AttendanceRecordList sameRecordAndSize = new AttendanceRecordList(3)
                .addAttendance(ALICE, new Week(2), new Attendance(VALID_ATTENDANCE_80))
                .addAttendance(BENSON, new Week(3), new Attendance(VALID_ATTENDANCE_51))
                .addAttendance(CARL, new Week(3), new Attendance(VALID_ATTENDANCE_33));
        assertTrue(sameRecordAndSize.equals(attendanceRecordList));

        // same records, different size -> return false
        AttendanceRecordList sameRecordDifferentSize = new AttendanceRecordList(4)
                .addAttendance(ALICE, new Week(2), new Attendance(VALID_ATTENDANCE_80))
                .addAttendance(BENSON, new Week(3), new Attendance(VALID_ATTENDANCE_51))
                .addAttendance(CARL, new Week(3), new Attendance(VALID_ATTENDANCE_33));
        assertFalse(sameRecordDifferentSize.equals(attendanceRecordList));

        // different records return false
        AttendanceRecordList editedList = attendanceRecordList
                .editAttendance(ALICE, new Week(2), new Attendance(VALID_ATTENDANCE_33));
        assertFalse(editedList.equals(attendanceRecordList));
    }

    /**
     * Creates an {@code AttendanceRecordList} with {@code RECORD_ALICE_80} at the first, valid week.
     * The rest of the 6 elements are empty {@code AttendanceRecords}.
     */
    private AttendanceRecordList createAliceRecordList() {
        List<AttendanceRecord> list = new ArrayList<>();
        list.add(RECORD_ALICE_80);
        for (int i = 0; i < 6; i++) {
            list.add(RECORD_EMPTY);
        }
        return new AttendanceRecordList(list);
    }
}
