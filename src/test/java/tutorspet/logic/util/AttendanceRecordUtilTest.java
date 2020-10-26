package tutorspet.logic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorspet.commons.core.Messages.MESSAGE_DUPLICATE_ATTENDANCE;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_51;
import static tutorspet.logic.util.AttendanceRecordUtil.addAttendance;
import static tutorspet.logic.util.AttendanceRecordUtil.removeAttendance;
import static tutorspet.logic.util.AttendanceRecordUtil.setAttendance;
import static tutorspet.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.student.Student;
import tutorspet.testutil.AttendanceRecordBuilder;
import tutorspet.testutil.StudentBuilder;

public class AttendanceRecordUtilTest {

    private static final Attendance DEFAULT_ATTENDANCE = new Attendance(VALID_PARTICIPATION_SCORE_33);
    private static final Student DEFAULT_STUDENT = new StudentBuilder().build();

    @Test
    public void addAttendance_validParameters_success() throws Exception {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().withEntry(DEFAULT_STUDENT.getUuid(), DEFAULT_ATTENDANCE).build();

        AttendanceRecord actualAttendanceRecord = addAttendance(attendanceRecord, DEFAULT_STUDENT, DEFAULT_ATTENDANCE);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void addAttendance_existingAttendance_throwsCommandException() {
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(DEFAULT_STUDENT.getUuid(), DEFAULT_ATTENDANCE).build();

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ATTENDANCE, () ->
                addAttendance(attendanceRecord, DEFAULT_STUDENT, DEFAULT_ATTENDANCE));
    }

    @Test
    public void addAttendance_nullAttendanceRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addAttendance(null, DEFAULT_STUDENT, DEFAULT_ATTENDANCE));
    }

    @Test
    public void addAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> addAttendance(attendanceRecord, null, DEFAULT_ATTENDANCE));
    }

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> addAttendance(attendanceRecord, DEFAULT_STUDENT, null));
    }

    @Test
    public void setAttendance_validParameters_success() throws CommandException {
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(DEFAULT_STUDENT.getUuid(), DEFAULT_ATTENDANCE).build();
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().withEntry(DEFAULT_STUDENT.getUuid(), attendanceToSet).build();

        AttendanceRecord actualAttendanceRecord = setAttendance(attendanceRecord, DEFAULT_STUDENT, attendanceToSet);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void setAttendance_noExistingAttendance_throwsCommandException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                setAttendance(attendanceRecord, DEFAULT_STUDENT, DEFAULT_ATTENDANCE));
    }

    @Test
    public void setAttendance_nullAttendanceRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> setAttendance(null, DEFAULT_STUDENT, DEFAULT_ATTENDANCE));
    }

    @Test
    public void setAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> setAttendance(attendanceRecord, null, DEFAULT_ATTENDANCE));
    }

    @Test
    public void setAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> setAttendance(attendanceRecord, DEFAULT_STUDENT, null));
    }

    @Test
    public void removeAttendance_validParameters_success() throws Exception {
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(DEFAULT_STUDENT.getUuid(), DEFAULT_ATTENDANCE).build();
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().build();

        AttendanceRecord actualAttendanceRecord = removeAttendance(attendanceRecord, DEFAULT_STUDENT);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void removeAttendance_noExistingAttendance_throwsCommandException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                removeAttendance(attendanceRecord, DEFAULT_STUDENT));
    }

    @Test
    public void removeAttendance_nullAttendanceRecord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> removeAttendance(null, DEFAULT_STUDENT));
    }

    @Test
    public void removeAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> removeAttendance(attendanceRecord, null));
    }
}
