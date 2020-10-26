package tutorspet.logic.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tutorspet.commons.core.Messages.MESSAGE_DUPLICATE_ATTENDANCE;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSON_ATTENDED;
import static tutorspet.logic.commands.CommandTestUtil.VALID_ATTENDANCE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_80;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.util.LessonUtil.addAttendanceToLesson;
import static tutorspet.logic.util.LessonUtil.deleteAllStudentsFromLesson;
import static tutorspet.logic.util.LessonUtil.deleteAttendanceFromLesson;
import static tutorspet.logic.util.LessonUtil.deleteStudentFromLesson;
import static tutorspet.logic.util.LessonUtil.editAttendanceInLesson;
import static tutorspet.logic.util.LessonUtil.getAbsentWeekFromLesson;
import static tutorspet.logic.util.LessonUtil.getAttendanceFromLesson;
import static tutorspet.logic.util.LessonUtil.getParticipationScoreFromLesson;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.LessonBuilder.insertAttendanceRecords;
import static tutorspet.testutil.TypicalAttendanceRecord.RECORD_ALICE_51_BENSON_33;
import static tutorspet.testutil.TypicalAttendanceRecord.RECORD_EMPTY;
import static tutorspet.testutil.TypicalStudent.ALICE;
import static tutorspet.testutil.TypicalStudent.BENSON;
import static tutorspet.testutil.TypicalStudent.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.Week;
import tutorspet.model.lesson.Lesson;
import tutorspet.testutil.LessonBuilder;

public class LessonUtilTest {

    private static final Lesson DEFAULT_LESSON =
            insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                    RECORD_EMPTY, RECORD_ALICE_51_BENSON_33);

    @Test
    public void deleteStudentFromLesson_validParameters_success() {
        AttendanceRecord record =
                new AttendanceRecord(Map.of(BENSON.getUuid(), VALID_ATTENDANCE_33));
        Lesson expectedLesson = insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                RECORD_EMPTY, record);

        assertEquals(expectedLesson, deleteStudentFromLesson(DEFAULT_LESSON, ALICE));
    }

    @Test
    public void deleteStudentFromLesson_noExistingStudent_success() {
        assertEquals(DEFAULT_LESSON, deleteStudentFromLesson(DEFAULT_LESSON, CARL));
    }

    @Test
    public void deleteStudentFromLesson_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                deleteStudentFromLesson(null, ALICE));
        assertThrows(NullPointerException.class, () ->
                deleteStudentFromLesson(DEFAULT_LESSON, null));
    }

    @Test
    public void deleteAllStudentFromLesson_validParameters_success() {
        Lesson expectedLesson = insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                RECORD_EMPTY, new AttendanceRecord());

        assertEquals(expectedLesson, deleteAllStudentsFromLesson(DEFAULT_LESSON));
    }

    @Test
    public void deleteAllStudentFromLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                deleteAllStudentsFromLesson(null));
    }

    @Test
    public void addAttendanceToLesson_validParameters_success() throws Exception {
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        AttendanceRecord record = new AttendanceRecord(Map.of(ALICE.getUuid(), attendanceToAdd));
        Lesson expectedLesson = insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                record, RECORD_ALICE_51_BENSON_33);

        assertEquals(expectedLesson, addAttendanceToLesson(DEFAULT_LESSON, ALICE, VALID_WEEK_1, attendanceToAdd));
    }

    @Test
    public void addAttendanceFromLesson_existingAttendance_throwsCommandException() {
        Week week = new Week(Index.fromOneBased(2));
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_ATTENDANCE, () ->
                addAttendanceToLesson(DEFAULT_LESSON, BENSON, week, attendanceToAdd));
    }

    @Test
    public void addAttendanceFromLesson_invalidWeek_throwsCommandException() {
        Attendance attendanceToAdd = new Attendance(VALID_PARTICIPATION_SCORE_80);
        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                addAttendanceToLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_5, attendanceToAdd));
    }

    @Test
    public void editAttendanceInLesson_validParameters_success() throws Exception {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        AttendanceRecord record =
                new AttendanceRecord(Map.of(ALICE.getUuid(), attendanceToSet, BENSON.getUuid(), VALID_ATTENDANCE_33));
        Lesson expectedLesson = insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                RECORD_EMPTY, record);
        Week week = new Week(Index.fromOneBased(2));

        assertEquals(expectedLesson, editAttendanceInLesson(DEFAULT_LESSON, ALICE, week, attendanceToSet));
    }

    @Test
    public void editAttendanceFromLesson_noExistingAttendance_throwsCommandException() {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                editAttendanceInLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_1, attendanceToSet));
    }

    @Test
    public void editAttendanceFromLesson_invalidWeek_throwsCommandException() {
        Attendance attendanceToSet = new Attendance(VALID_PARTICIPATION_SCORE_80);
        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                editAttendanceInLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_5, attendanceToSet));
    }

    @Test
    public void deleteAttendanceFromLesson_validParameters_success() throws Exception {
        AttendanceRecord record =
                new AttendanceRecord(Map.of(BENSON.getUuid(), VALID_ATTENDANCE_33));
        Lesson expectedLesson = insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                RECORD_EMPTY, record);
        Week week = new Week(Index.fromOneBased(2));

        assertEquals(expectedLesson, deleteAttendanceFromLesson(DEFAULT_LESSON, ALICE, week));
    }

    @Test
    public void deleteAttendanceFromLesson_noExistingAttendance_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                deleteAttendanceFromLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_1));
    }

    @Test
    public void deleteAttendanceFromLesson_invalidWeek_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                deleteAttendanceFromLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_5));
    }

    @Test
    public void getAttendanceFromLesson_validParameters_success() throws Exception {
        Week week = new Week(Index.fromOneBased(2));
        assertEquals(VALID_ATTENDANCE_33, getAttendanceFromLesson(DEFAULT_LESSON, BENSON, week));
    }

    @Test
    public void getAttendanceFromLesson_noExistingAttendance_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_MISSING_STUDENT_ATTENDANCE, () ->
                getAttendanceFromLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_1));
    }

    @Test
    public void getAttendanceFromLesson_invalidWeek_throwsCommandException() {
        assertThrows(CommandException.class, MESSAGE_INVALID_WEEK, () ->
                getAttendanceFromLesson(DEFAULT_LESSON, BENSON, VALID_WEEK_5));
    }

    @Test
    public void getParticipationScoreFromLesson_validParameter_success() throws CommandException {
        double avgParticipationScoreAlice = 51.0;
        assertEquals(avgParticipationScoreAlice,
                getParticipationScoreFromLesson(DEFAULT_LESSON, ALICE));
    }

    @Test
    public void getParticipationScore_invalidStudentAttendance_throwsCommandException() {
        Lesson lesson =
                insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                        RECORD_EMPTY, RECORD_EMPTY);
        assertThrows(CommandException.class, MESSAGE_NO_LESSON_ATTENDED, () ->
                getParticipationScoreFromLesson(lesson, CARL));
    }

    @Test
    public void getParticipationScore_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getParticipationScoreFromLesson(
                null, ALICE));
        assertThrows(NullPointerException.class, () -> getParticipationScoreFromLesson(
                DEFAULT_LESSON, null));
    }

    @Test
    public void getAbsentWeekFromLesson_validParameter_success() {
        List<Integer> result = new ArrayList<>(Collections.singletonList(1));
        assertEquals(result,
                getAbsentWeekFromLesson(DEFAULT_LESSON, ALICE));
    }

    @Test
    public void getAbsentWeekLesson_validParameterAll_success() {
        Lesson lesson =
                insertAttendanceRecords(new LessonBuilder().withNumberOfOccurrences(2).build(),
                        RECORD_EMPTY, RECORD_EMPTY);
        List<Integer> result = new ArrayList<>(Arrays.asList(1, 2));
        assertEquals(result,
                getAbsentWeekFromLesson(lesson, ALICE));
    }

    @Test
    public void getAbsentWeekFromLesson_nullParameters_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> getAbsentWeekFromLesson(
                null, ALICE));
        assertThrows(NullPointerException.class, () -> getAbsentWeekFromLesson(
                DEFAULT_LESSON, null));
    }
}
