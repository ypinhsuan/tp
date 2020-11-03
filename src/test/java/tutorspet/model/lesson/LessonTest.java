package tutorspet.model.lesson;

import static java.time.LocalTime.parse;
import static java.util.UUID.fromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.logic.commands.CommandTestUtil.VALID_DAY_FRI_LESSON_FRI_8_TO_10;
import static tutorspet.logic.commands.CommandTestUtil.VALID_DAY_WED_LESSON_WED_2_TO_4;
import static tutorspet.logic.commands.CommandTestUtil.VALID_END_TIME_1600_LESSON_WED_2_TO_4;
import static tutorspet.logic.commands.CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_START_TIME_0900;
import static tutorspet.logic.commands.CommandTestUtil.VALID_START_TIME_1400_LESSON_WED_2_TO_4;
import static tutorspet.logic.commands.CommandTestUtil.VALID_UUID_AMY;
import static tutorspet.logic.commands.CommandTestUtil.VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4;
import static tutorspet.model.lesson.Lesson.TIME_FORMATTER;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalLesson.LESSON_FRI_8_TO_10;
import static tutorspet.testutil.TypicalLesson.LESSON_WED_2_TO_4;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.AttendanceRecordList;
import tutorspet.testutil.LessonBuilder;

public class LessonTest {

    private static final LocalTime VALID_START_TIME =
            parse(VALID_START_TIME_1400_LESSON_WED_2_TO_4);
    private static final LocalTime VALID_END_TIME =
            parse(VALID_END_TIME_1600_LESSON_WED_2_TO_4);
    private static final LocalTime VALID_TIME_0900 = LocalTime.of(9, 0);
    private static final LocalTime VALID_TIME_1000 = LocalTime.of(10, 0);
    private static final LocalTime VALID_TIME_1159 = LocalTime.of(11, 59);
    private static final LocalTime VALID_TIME_1200 = LocalTime.of(12, 0);
    private static final LocalTime VALID_TIME_1201 = LocalTime.of(12, 1);
    private static final LocalTime VALID_TIME_1400 = LocalTime.of(14, 0);
    private static final Venue VALID_VENUE = new Venue(VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4);
    private static final NumberOfOccurrences VALID_NUMBER_OF_OCCURRENCES =
            new NumberOfOccurrences(1);
    private static final Attendance VALID_ATTENDANCE = new Attendance(VALID_PARTICIPATION_SCORE_33);
    private static final AttendanceRecord VALID_ATTENDANCE_RECORD =
            new AttendanceRecord(Map.of(fromString(VALID_UUID_AMY), VALID_ATTENDANCE));
    private static final AttendanceRecordList VALID_ATTENDANCE_RECORD_LIST =
            new AttendanceRecordList(Collections.singletonList(VALID_ATTENDANCE_RECORD));

    @Test
    public void constructor_newLesson_hasNewAttendanceRecordList() {
        Lesson lesson = new Lesson(VALID_START_TIME, VALID_END_TIME,
                VALID_DAY_WED_LESSON_WED_2_TO_4, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        assertEquals(VALID_START_TIME, lesson.getStartTime());
        assertEquals(VALID_END_TIME, lesson.getEndTime());
        assertEquals(VALID_DAY_WED_LESSON_WED_2_TO_4, lesson.getDay());
        assertEquals(VALID_NUMBER_OF_OCCURRENCES, lesson.getNumberOfOccurrences());
        assertEquals(VALID_VENUE, lesson.getVenue());
        assertEquals(new AttendanceRecordList(VALID_NUMBER_OF_OCCURRENCES), lesson.getAttendanceRecordList());
    }

    @Test
    public void constructor_existingAttendanceRecordList_hasCorrectAttendanceRecordList() {
        Lesson lesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        assertEquals(VALID_START_TIME, lesson.getStartTime());
        assertEquals(VALID_END_TIME, lesson.getEndTime());
        assertEquals(VALID_DAY_WED_LESSON_WED_2_TO_4, lesson.getDay());
        assertEquals(VALID_NUMBER_OF_OCCURRENCES, lesson.getNumberOfOccurrences());
        assertEquals(VALID_VENUE, lesson.getVenue());
        assertEquals(VALID_ATTENDANCE_RECORD_LIST, lesson.getAttendanceRecordList());
    }

    @Test
    public void constructor_attendanceRecordListMismatch_throwsAssertionError() {
        assertThrows(AssertionError.class, () ->
                new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DAY_WED_LESSON_WED_2_TO_4,
                        VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE,
                        new AttendanceRecordList(
                                new NumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES.value + 1))));
    }

    @Test
    public void isValidStartTimeEndTime() {
        // invalid start time and end time
        assertFalse(Lesson.isValidStartTimeEndTime(LocalTime.of(12, 0), LocalTime.of(10, 0)));
        assertFalse(Lesson.isValidStartTimeEndTime(LocalTime.of(12, 0), LocalTime.of(12, 0)));

        // valid start time and end time
        assertTrue(Lesson.isValidStartTimeEndTime(LocalTime.of(8, 0), LocalTime.of(14, 0)));
        assertTrue(Lesson.isValidStartTimeEndTime(LocalTime.of(8, 0), LocalTime.of(8, 1)));
    }

    @Test
    public void isSameLesson() {
        // same object -> returns true
        assertTrue(LESSON_FRI_8_TO_10.isSameLesson(LESSON_FRI_8_TO_10));

        // same values -> returns true
        Lesson lessonCopy = new LessonBuilder(LESSON_FRI_8_TO_10).build();
        assertTrue(LESSON_FRI_8_TO_10.isSameLesson(lessonCopy));

        // null -> returns false
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(null));

        // different start time -> returns false
        Lesson editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withStartTime(parse(VALID_START_TIME_0900,
                        TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withEndTime(parse(VALID_END_TIME_1600_LESSON_WED_2_TO_4,
                        TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withDay(VALID_DAY_WED_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different number of occurrences -> returns true
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4).build();
        assertTrue(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different attendance record list and same number of occurrences -> return true
        List<AttendanceRecord> records = new ArrayList<>(Collections.nCopies(13, new AttendanceRecord()));
        records.set(0, VALID_ATTENDANCE_RECORD);
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withAttendanceRecordList(new AttendanceRecordList(records)).build();
        assertTrue(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));
    }

    @Test
    public void isOverlapLesson() {
        Lesson lessonWed0900To1200 = new Lesson(VALID_TIME_0900, VALID_TIME_1200, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonWed1000To1200 = new Lesson(VALID_TIME_1000, VALID_TIME_1200, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonWed1000to1400 = new Lesson(VALID_TIME_1000, VALID_TIME_1400, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonWed1200To1400 = new Lesson(VALID_TIME_1200, VALID_TIME_1400, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonWed1000To1201 = new Lesson(VALID_TIME_1000, VALID_TIME_1201, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonWed1000To1159 = new Lesson(VALID_TIME_1000, VALID_TIME_1159, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        Lesson lessonFri1000To1200 = new Lesson(VALID_TIME_1000, VALID_TIME_1200, VALID_DAY_FRI_LESSON_FRI_8_TO_10,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);

        // same day
        assertTrue(lessonWed1000To1200.isOverlapLesson(lessonWed1000To1200));
        assertTrue(lessonWed0900To1200.isOverlapLesson(lessonWed1000To1200));
        assertTrue(lessonWed1000to1400.isOverlapLesson(lessonWed1000To1200));
        assertTrue(lessonWed1000To1201.isOverlapLesson(lessonWed1200To1400));
        assertFalse(lessonWed1200To1400.isOverlapLesson(lessonWed1000To1200));
        assertFalse(lessonWed1200To1400.isOverlapLesson(lessonWed1000To1159));

        // different day
        assertFalse(lessonWed1000To1200.isOverlapLesson(lessonFri1000To1200));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        Lesson lesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        String expectedString = (new StringBuilder()).append(VALID_DAY_WED_LESSON_WED_2_TO_4)
                .append(" ")
                .append(TIME_FORMATTER.format(VALID_START_TIME))
                .append(" to ")
                .append(TIME_FORMATTER.format(VALID_END_TIME))
                .append("\n")
                .append("Venue: ")
                .append(VALID_VENUE.venue)
                .append(" Number of occurrences: ")
                .append(VALID_NUMBER_OF_OCCURRENCES.value).toString();
        assertEquals(expectedString, lesson.toString());
    }

    @Test
    public void printLesson_returnsCorrectFormat() {
        Lesson lesson = new Lesson(VALID_START_TIME, VALID_END_TIME, VALID_DAY_WED_LESSON_WED_2_TO_4,
                VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE, VALID_ATTENDANCE_RECORD_LIST);
        String expectedString = (new StringBuilder()).append(VALID_DAY_WED_LESSON_WED_2_TO_4)
                .append(" ")
                .append(TIME_FORMATTER.format(VALID_START_TIME))
                .append(" to ")
                .append(TIME_FORMATTER.format(VALID_END_TIME)).toString();
        assertEquals(expectedString, lesson.printLesson());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(LESSON_FRI_8_TO_10.equals(LESSON_FRI_8_TO_10));

        // same values -> returns true
        Lesson lessonCopy = new LessonBuilder(LESSON_FRI_8_TO_10).build();
        assertTrue(LESSON_FRI_8_TO_10.equals(lessonCopy));

        // null -> returns false
        assertFalse(LESSON_FRI_8_TO_10.equals(null));

        // different type -> returns false
        assertFalse(LESSON_FRI_8_TO_10.equals(1));

        // different lesson -> returns false
        assertFalse(LESSON_FRI_8_TO_10.equals(LESSON_WED_2_TO_4));

        // different start time -> returns false
        Lesson editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withStartTime(parse(VALID_START_TIME_0900,
                        TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withEndTime(parse(VALID_END_TIME_1600_LESSON_WED_2_TO_4,
                        TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withDay(VALID_DAY_WED_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different venue -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withVenue(VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different number of occurrences -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different attendance record list and same number of occurrences -> return false
        List<AttendanceRecord> records = new ArrayList<>(Collections.nCopies(13, new AttendanceRecord()));
        records.set(0, VALID_ATTENDANCE_RECORD);
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withAttendanceRecordList(new AttendanceRecordList(records)).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));
    }
}
