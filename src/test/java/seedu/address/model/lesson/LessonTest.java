package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_OCCURRENCES_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_WED_2_TO_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_WED_2_TO_4;
import static seedu.address.testutil.TypicalLesson.FRI_8_TO_10;
import static seedu.address.testutil.TypicalLesson.WED_2_TO_4;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void isSameLesson() {
        // same object -> returns true
        assertTrue(FRI_8_TO_10.isSameLesson(FRI_8_TO_10));

        // same values -> returns true
        Lesson lessonCopy = new LessonBuilder(FRI_8_TO_10).build();
        assertTrue(FRI_8_TO_10.isSameLesson(lessonCopy));

        // null -> returns false
        assertFalse(FRI_8_TO_10.isSameLesson(null));

        // different start time -> returns false
        Lesson editedLesson = new LessonBuilder(FRI_8_TO_10).withStartTime(VALID_START_TIME_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.isSameLesson(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withEndTime(VALID_END_TIME_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.isSameLesson(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withDay(VALID_DAY_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.isSameLesson(editedLesson));

        // different venue -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withVenue(VALID_VENUE_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.isSameLesson(editedLesson));

        // different number of occurrences -> returns true
        editedLesson = new LessonBuilder(FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_WED_2_TO_4).build();
        assertTrue(FRI_8_TO_10.isSameLesson(editedLesson));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(FRI_8_TO_10.equals(FRI_8_TO_10));

        // same values -> returns true
        Lesson lessonCopy = new LessonBuilder(FRI_8_TO_10).build();
        assertTrue(FRI_8_TO_10.equals(lessonCopy));

        // null -> returns false
        assertFalse(FRI_8_TO_10.equals(null));

        // different type -> returns false
        assertFalse(FRI_8_TO_10.equals(1));

        // different lesson -> returns false
        assertFalse(FRI_8_TO_10.equals(WED_2_TO_4));

        // different start time -> returns false
        Lesson editedLesson = new LessonBuilder(FRI_8_TO_10).withStartTime(VALID_START_TIME_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.equals(editedLesson));

        // different end time -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withEndTime(VALID_END_TIME_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.equals(editedLesson));

        // different day -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withDay(VALID_DAY_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.equals(editedLesson));

        // different venue -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10).withVenue(VALID_VENUE_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.equals(editedLesson));

        // different number of occurrences -> returns false
        editedLesson = new LessonBuilder(FRI_8_TO_10)
                .withNumberOfOccurrences(VALID_NUMBER_OF_OCCURRENCES_WED_2_TO_4).build();
        assertFalse(FRI_8_TO_10.equals(editedLesson));
    }
}
