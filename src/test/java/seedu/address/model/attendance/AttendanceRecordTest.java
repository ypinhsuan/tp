package seedu.address.model.attendance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_33;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_51;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_80;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_ALICE_80;
import static seedu.address.testutil.TypicalAttendanceRecord.RECORD_EMPTY;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BENSON;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.DuplicateAttendanceException;
import seedu.address.testutil.AttendanceRecordBuilder;

public class AttendanceRecordTest {

    private final AttendanceRecord attendanceRecord = new AttendanceRecord();

    @Test
    public void constructor_emptyAttendanceRecord() {
        assertTrue(attendanceRecord.getAttendanceRecord().isEmpty());
    }

    @Test
    public void constructor_nonEmptyAttendanceRecord() {
        HashMap<UUID, Attendance> hashMap = new HashMap<>();
        UUID key = ALICE.getUuid();
        Attendance value = new Attendance(VALID_ATTENDANCE_33);
        hashMap.put(key, value);
        AttendanceRecord record = new AttendanceRecord(hashMap);

        assertTrue(record.getAttendanceRecord().containsKey(key));
        assertEquals(value, record.getAttendanceRecord().get(key));
    }

    @Test
    public void getAttendance_nullUuid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.getAttendance(null));
    }

    @Test
    public void getAttendance_emptyAttendanceRecord_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> attendanceRecord.getAttendance(ALICE.getUuid()));
    }

    @Test
    public void getAttendance_nonExistingUuid_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> attendanceRecord.getAttendance(ALICE.getUuid()));
    }

    @Test
    public void getAttendance_existingUuid_success() {
        AttendanceRecord expectedAttendanceRecord = attendanceRecord
                .addAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80));
        assertEquals(new Attendance(VALID_ATTENDANCE_80),
                expectedAttendanceRecord.getAttendance(ALICE.getUuid()));
    }

    @Test
    public void addAttendance_nullUuid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.addAttendance(
                null, new Attendance(10)));
    }

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.addAttendance(
                ALICE.getUuid(), null));
    }

    @Test
    public void addAttendance_existingUuid_throwsDuplicateAttendanceException() {
        AttendanceRecord duplicateAttendanceRecord = attendanceRecord
                .addAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80));
        assertThrows(DuplicateAttendanceException.class, () -> duplicateAttendanceRecord.addAttendance(
                ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80)));
    }

    @Test
    public void addAttendance_nonExistingUuid_success() {
        AttendanceRecord updatedRecord = attendanceRecord
                .addAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80));

        Map<UUID, Attendance> map = new HashMap<>();
        map.put(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80));
        AttendanceRecord expectedRecord = new AttendanceRecord(map);
        assertEquals(expectedRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());

        //check immutability
        assertNotEquals(attendanceRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());
    }

    @Test
    public void editAttendance_nullUuid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.editAttendance(null, new Attendance(10)));
    }

    @Test
    public void editAttendance_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.editAttendance(ALICE.getUuid(), null));
    }

    @Test
    public void editAttendance_nonExistingUuid_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> attendanceRecord.editAttendance(
                ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80)));
    }

    @Test
    public void editAttendance_existingUuid_success() {
        AttendanceRecord updatedRecord = attendanceRecord
                .addAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80))
                .editAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_33));

        Map<UUID, Attendance> map = new HashMap<>();
        map.put(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_33));
        AttendanceRecord expectedRecord = new AttendanceRecord(map);
        assertEquals(expectedRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());

        //checks immutability
        assertNotEquals(attendanceRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());
    }

    @Test
    public void deleteAttendance_nullUuid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> attendanceRecord.deleteAttendance(null));
    }

    @Test
    public void deleteAttendance_nonExistingUuid_throwsAttendanceNotFoundException() {
        assertThrows(AttendanceNotFoundException.class, () -> attendanceRecord.deleteAttendance(ALICE.getUuid()));
    }

    @Test
    public void deleteAttendance_existingUuid_success() {
        AttendanceRecord updatedRecord = attendanceRecord
                .addAttendance(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80))
                .addAttendance(BENSON.getUuid(), new Attendance(VALID_ATTENDANCE_51))
                .deleteAttendance(ALICE.getUuid());

        Map<UUID, Attendance> map = new HashMap<>();
        map.put(BENSON.getUuid(), new Attendance(VALID_ATTENDANCE_51));
        AttendanceRecord expectedRecord = new AttendanceRecord(map);
        assertEquals(expectedRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());

        //checks immutability
        assertNotEquals(attendanceRecord.getAttendanceRecord(), updatedRecord.getAttendanceRecord());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(RECORD_ALICE_80.equals(RECORD_ALICE_80));
        assertTrue(RECORD_EMPTY.equals(RECORD_EMPTY));

        // null -> returns false
        assertFalse(RECORD_ALICE_80.equals(null));
        assertFalse(RECORD_EMPTY.equals(null));

        // different types -> returns false
        assertFalse(RECORD_ALICE_80.equals(RECORD_ALICE_80.getAttendanceRecord()));

        // same attendance record -> returns true
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder()
                .withEntry(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_80)).build();
        assertTrue(RECORD_ALICE_80.equals(attendanceRecord));

        // different attendance record -> returns false
        AttendanceRecord attendanceRecordWrongUuid = new AttendanceRecordBuilder()
                .withEntry(BENSON.getUuid(), new Attendance(VALID_ATTENDANCE_80)).build();
        assertFalse(RECORD_ALICE_80.equals(attendanceRecordWrongUuid));

        AttendanceRecord attendanceRecordWrongAttendance = new AttendanceRecordBuilder()
                .withEntry(ALICE.getUuid(), new Attendance(VALID_ATTENDANCE_33)).build();
        assertFalse(RECORD_ALICE_80.equals(attendanceRecordWrongAttendance));
    }
}
