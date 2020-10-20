package tutorspet.logic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.logic.commands.AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_51;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.util.AttendanceRecordListUtil.addAttendanceToAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.editAttendanceInAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.getAttendanceFromAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.getAttendances;
import static tutorspet.logic.util.AttendanceRecordListUtil.removeAttendanceFromAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordUtil.addAttendance;
import static tutorspet.logic.util.AttendanceRecordUtil.setAttendance;
import static tutorspet.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.AttendanceRecordList;
import tutorspet.model.attendance.Week;
import tutorspet.model.student.Student;
import tutorspet.testutil.AttendanceRecordBuilder;
import tutorspet.testutil.StudentBuilder;

public class AttendanceRecordListUtilTest {

    @Test
    public void addAttendance_validParameters_success() throws CommandException {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        AttendanceRecord expectedRecord = addAttendance(record, student, attendance);
        AttendanceRecordList expectedRecordList = new AttendanceRecordList(Collections.singletonList(expectedRecord));

        AttendanceRecordList actualRecordList =
                addAttendanceToAttendanceRecordList(recordList, student, week, attendance);
        assertEquals(expectedRecordList, actualRecordList);
    }

    @Test
    public void addAttendance_invalidWeek_throwsCommandException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Week week = VALID_WEEK_5;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                addAttendanceToAttendanceRecordList(recordList, student, week, attendance));
    }

    @Test
    public void addAttendance_existingAttendance_throwsCommandException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ATTENDANCE, () ->
                addAttendanceToAttendanceRecordList(recordList, student, week, attendance));
    }

    @Test
    public void addAttendance_nullAttendanceRecordList_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Week week = VALID_WEEK_1;

        assertThrows(NullPointerException.class, () ->
                addAttendanceToAttendanceRecordList(null, student, week, attendance));
    }

    @Test
    public void addAttendance_nullStudent_throwsNullPointerException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                addAttendanceToAttendanceRecordList(recordList, null, week, attendance));
    }

    @Test
    public void addAttendance_nullWeek_throwsNullPointerException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                addAttendanceToAttendanceRecordList(recordList, null, week, attendance));
    }

    @Test
    public void addAttendance_nullAttendance_throwsNullPointerException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                addAttendanceToAttendanceRecordList(recordList, student, week, null));
    }

    @Test
    public void editAttendance_validParameters_success() throws CommandException {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);
        AttendanceRecord expectedRecord = setAttendance(record, student, attendanceToSet);
        AttendanceRecordList expectedRecordList = new AttendanceRecordList(Collections.singletonList(expectedRecord));

        AttendanceRecordList actualRecordList =
                editAttendanceInAttendanceRecordList(recordList, student, week, attendanceToSet);
        assertEquals(expectedRecordList, actualRecordList);
    }

    @Test
    public void editAttendance_invalidWeek_throwsCommandException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_5;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);

        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                editAttendanceInAttendanceRecordList(recordList, student, week, attendanceToSet));
    }

    @Test
    public void editAttendance_noExistingAttendance_throwsCommandException() {
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                editAttendanceInAttendanceRecordList(recordList, student, week, attendanceToSet));
    }

    @Test
    public void editAttendance_nullAttendanceRecordList_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Week week = VALID_WEEK_1;
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);

        assertThrows(NullPointerException.class, () ->
                editAttendanceInAttendanceRecordList(null, student, week, attendanceToSet));
    }

    @Test
    public void editAttendance_nullStudent_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);

        assertThrows(NullPointerException.class, () ->
                editAttendanceInAttendanceRecordList(recordList, null, week, attendanceToSet));
    }

    @Test
    public void editAttendance_nullWeek_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_51);

        assertThrows(NullPointerException.class, () ->
                editAttendanceInAttendanceRecordList(recordList, student, null, attendanceToSet));
    }

    @Test
    public void editAttendance_nullAttendance_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                editAttendanceInAttendanceRecordList(recordList, student, week, null));
    }

    @Test
    public void removeAttendance_validParameters_success() throws CommandException {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        AttendanceRecord expectedRecord = new AttendanceRecord();
        AttendanceRecordList expectedRecordList = new AttendanceRecordList(Collections.singletonList(expectedRecord));

        AttendanceRecordList actualRecordList =
                removeAttendanceFromAttendanceRecordList(recordList, student, week);
        assertEquals(expectedRecordList, actualRecordList);
    }

    @Test
    public void removeAttendance_invalidWeek_throwsCommandException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_5;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                removeAttendanceFromAttendanceRecordList(recordList, student, week));
    }

    @Test
    public void removeAttendance_noExistingAttendance_throwsCommandException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                removeAttendanceFromAttendanceRecordList(recordList, student, week));
    }

    @Test
    public void removeAttendance_nullAttendanceRecordList_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Week week = VALID_WEEK_1;

        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(null, student, week));
    }

    @Test
    public void removeAttendance_nullStudent_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        Week week = VALID_WEEK_1;
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(recordList, null, week));
    }

    @Test
    public void removeAttendance_nullWeek_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(recordList, student, null));
    }

    @Test
    public void getAttendanceFromLesson_hasAttendance_returnsAttendance() throws Exception {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        Week week = VALID_WEEK_1;
        assertEquals(attendance, getAttendanceFromAttendanceRecordList(recordList, student, week));
    }

    @Test
    public void getAttendanceFromLesson_invalidWeek_throwsCommandException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        Week week = VALID_WEEK_5;
        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                getAttendanceFromAttendanceRecordList(recordList, student, week));
    }

    @Test
    public void getAttendanceFromLesson_noExistingAttendance_throwsCommandException() {
        AttendanceRecord record = new AttendanceRecordBuilder().build();
        Student student = new StudentBuilder().build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        Week week = VALID_WEEK_1;
        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                getAttendanceFromAttendanceRecordList(recordList, student, week));
    }

    @Test
    public void getAttendanceFromLesson_nullAttendanceRecordList_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        Week week = VALID_WEEK_1;
        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(null, student, week));
    }

    @Test
    public void getAttendanceFromLesson_nullStudent_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        Week week = VALID_WEEK_1;
        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(recordList, null, week));
    }

    @Test
    public void getAttendanceFromLesson_nullParameters_throwsNullPointerException() {
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord record = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Collections.singletonList(record));

        assertThrows(NullPointerException.class, () ->
                removeAttendanceFromAttendanceRecordList(recordList, student, null));
    }

    @Test
    public void getAttendances_returnsAttendances() {
        AttendanceRecord recordWeekOne = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord recordWeekTwo = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Arrays.asList(recordWeekOne, recordWeekTwo));

        List<Optional<Attendance>> expectedList = Arrays.asList(Optional.empty(), Optional.of(attendance));
        assertEquals(expectedList, getAttendances(recordList, student));
    }

    @Test
    public void getAttendances_nullAttendanceRecordList_throwsNullPointerException() {
        Student student = new StudentBuilder().build();
        assertThrows(NullPointerException.class, () -> getAttendances(null, student));
    }

    @Test
    public void getAttendances_nullStudent_throwsNullPointerException() {
        AttendanceRecord recordWeekOne = new AttendanceRecordBuilder().build();
        Attendance attendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        Student student = new StudentBuilder().build();
        AttendanceRecord recordWeekTwo = new AttendanceRecordBuilder().withEntry(student.getUuid(), attendance).build();
        AttendanceRecordList recordList = new AttendanceRecordList(Arrays.asList(recordWeekOne, recordWeekTwo));

        assertThrows(NullPointerException.class, () -> getAttendances(recordList, null));
    }
}
