package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_7;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_80;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_ALICE_80;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_BENSON_51_CARL_33;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_EMPTY;
import static seedu.address.testutil.TypicalAttendanceRecord.getTypicalAttendanceRecord;
import static seedu.address.testutil.TypicalStudent.ALICE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.InvalidWeekException;

public class AttendanceRecordListTest {

    private static final AttendanceRecordList recordList =
            new AttendanceRecordList(VALID_NUMBER_OF_OCCURRENCES_7);

    private static final Week VALID_WEEK = new Week(Index.fromOneBased(1));
    private static final Week INVALID_WEEK = new Week(Index.fromOneBased(VALID_NUMBER_OF_OCCURRENCES_7 + 1));

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
    public void getAttendance_invalidWeek_throwsInvalidWeekException() {
        assertThrows(InvalidWeekException.class, () -> recordList.getAttendance(
                ALICE, INVALID_WEEK));
    }

    @Test
    public void getAttendance_nonExistingStudent_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> recordList.getAttendance(ALICE, VALID_WEEK));
    }

    @Test
    public void getAttendance_existingStudent_success() {
        AttendanceRecordList attendanceRecordList = createAliceRecordList();
        Attendance attendance = attendanceRecordList.getAttendance(ALICE, VALID_WEEK);
        assertEquals(new Attendance(VALID_PARTICIPATION_SCORE_80), attendance);
    }

    @Test
    public void getAttendanceRecord_invalidWeek_throwsInvalidWeekException() {
        assertThrows(InvalidWeekException.class, () -> recordList.getAttendanceRecord(INVALID_WEEK));
    }

    @Test
    public void getAttendanceRecord_validWeek_success() {
        AttendanceRecordList attendanceRecordList = createAliceRecordList();
        AttendanceRecord attendanceRecord = attendanceRecordList.getAttendanceRecord(VALID_WEEK);
        assertEquals(RECORD_ALICE_80, attendanceRecord);
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

        // same size, same attendance records -> return true
        List<AttendanceRecord> list = new ArrayList<>();
        list.add(RECORD_EMPTY);
        list.add(RECORD_ALICE_80);
        list.add(RECORD_BENSON_51_CARL_33);
        AttendanceRecordList sameSizeAndRecord = new AttendanceRecordList(list);
        assertTrue(attendanceRecordList.equals(sameSizeAndRecord));

        // different size, same attendance records -> return false
        list.add(RECORD_EMPTY);
        AttendanceRecordList differentSizeSameRecord = new AttendanceRecordList(list);
        assertFalse(attendanceRecordList.equals(differentSizeSameRecord));

        // same size, different attendance records -> return false
        list.remove(list.size() - 1);
        list.remove(list.size() - 1);
        list.add(RECORD_EMPTY);
        AttendanceRecordList sameSizeDifferentRecord = new AttendanceRecordList(list);
        assertFalse(attendanceRecordList.equals(sameSizeDifferentRecord));

        //different size, different attendance records -> return false
        list.add(RECORD_BENSON_51_CARL_33);
        AttendanceRecordList differentAttendanceRecordList = new AttendanceRecordList(list);
        assertFalse(attendanceRecordList.equals(differentAttendanceRecordList));
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
