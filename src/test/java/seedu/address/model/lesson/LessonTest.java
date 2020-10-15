package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_WED_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_1600_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_1400_LESSON_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4;
import static seedu.address.model.lesson.Lesson.TIME_FORMATTER;
import static seedu.address.testutil.TypicalLesson.LESSON_FRI_8_TO_10;
import static seedu.address.testutil.TypicalLesson.LESSON_WED_2_TO_4;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

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
                .withStartTime(LocalTime.parse(VALID_START_TIME_1400_LESSON_WED_2_TO_4, TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withEndTime(LocalTime.parse(VALID_END_TIME_1600_LESSON_WED_2_TO_4, TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10).withDay(VALID_DAY_WED_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different venue -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10).withVenue(VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));

        // different number of occurrences -> returns true
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4).build();
        assertTrue(LESSON_FRI_8_TO_10.isSameLesson(editedLesson));
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
                .withStartTime(LocalTime.parse(VALID_START_TIME_1400_LESSON_WED_2_TO_4, TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withEndTime(LocalTime.parse(VALID_END_TIME_1600_LESSON_WED_2_TO_4, TIME_FORMATTER)).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10).withDay(VALID_DAY_WED_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different venue -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10).withVenue(VALID_VENUE_COM1_B111_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));

        // different number of occurrences -> returns false
        editedLesson = new LessonBuilder(LESSON_FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_7_LESSON_WED_2_TO_4).build();
        assertFalse(LESSON_FRI_8_TO_10.equals(editedLesson));
    }
}
