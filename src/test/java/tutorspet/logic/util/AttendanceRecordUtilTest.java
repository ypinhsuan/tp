package tutorspet.logic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.logic.commands.AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;
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

    @Test
    public void addAttendance_validParameters_success() throws Exception {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();

        AttendanceRecord actualAttendanceRecord = addAttendance(attendanceRecord, student, attendance);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void addAttendance_existingAttendance_throwsCommandException() {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ATTENDANCE, () ->
                addAttendance(attendanceRecord, student, attendance));
    }

    @Test
    public void addAttendance_nullAttendanceRecord_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        assertThrows(NullPointerException.class, () -> addAttendance(null, student, attendance));
    }

    @Test
    public void addAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        assertThrows(NullPointerException.class, () -> addAttendance(attendanceRecord, null, attendance));
    }

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> addAttendance(attendanceRecord, student, null));
    }

    @Test
    public void setAttendance_validParameters_success() throws CommandException {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().withEntry(student.getUuid(), attendanceToSet).build();

        AttendanceRecord actualAttendanceRecord = setAttendance(attendanceRecord, student, attendanceToSet);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void setAttendance_noExistingAttendance_throwsCommandException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                setAttendance(attendanceRecord, student, attendance));
    }

    @Test
    public void setAttendance_nullAttendanceRecord_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        assertThrows(NullPointerException.class, () -> setAttendance(null, student, attendance));
    }

    @Test
    public void setAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        assertThrows(NullPointerException.class, () -> setAttendance(attendanceRecord, null, attendance));
    }

    @Test
    public void setAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> setAttendance(attendanceRecord, student, null));
    }

    @Test
    public void removeAttendance_validParameters_success() throws Exception {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        AttendanceRecord attendanceRecord =
                new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecord expectedAttendanceRecord =
                new AttendanceRecordBuilder().build();

        AttendanceRecord actualAttendanceRecord = removeAttendance(attendanceRecord, student);
        assertEquals(expectedAttendanceRecord, actualAttendanceRecord);
    }

    @Test
    public void removeAttendance_noExistingAttendance_throwsCommandException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                removeAttendance(attendanceRecord, student));
    }

    @Test
    public void removeAttendance_nullAttendanceRecord_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> removeAttendance(null, student));
    }

    @Test
    public void removeAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord attendanceRecord = new AttendanceRecordBuilder().build();
        assertThrows(NullPointerException.class, () -> removeAttendance(attendanceRecord, null));
    }
}
