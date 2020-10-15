package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedLesson.END_TIME_FIELD;
import static seedu.address.storage.JsonAdaptedLesson.INVALID_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedLesson.START_TIME_FIELD;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLesson.LESSON_WED_2_TO_4;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.NumberOfOccurrences;
import seedu.address.model.lesson.Venue;

public class JsonAdaptedLessonTest {

    private static final String INVALID_TIME = "1400";
    private static final String INVALID_DAY = "wed";
    private static final int INVALID_NUMBER_OF_OCCURRENCES = NumberOfOccurrences.UPPER_BOUND + 1;
    private static final String INVALID_VENUE = "!@#$%^&*";

    private static final String VALID_START_TIME = LESSON_WED_2_TO_4.getStartTime().toString();
    private static final String VALID_END_TIME = LESSON_WED_2_TO_4.getEndTime().toString();
    private static final String VALID_DAY = LESSON_WED_2_TO_4.getDay().toString();
    private static final int VALID_NUMBER_OF_OCCURRENCES = LESSON_WED_2_TO_4.getNumberOfOccurrences().value;
    private static final String VALID_VENUE = LESSON_WED_2_TO_4.getVenue().toString();

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(LESSON_WED_2_TO_4);
        assertEquals(LESSON_WED_2_TO_4, lesson.toModelType());
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                INVALID_TIME, VALID_END_TIME, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = String.format(INVALID_FIELD_MESSAGE_FORMAT, START_TIME_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                null, VALID_END_TIME, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, START_TIME_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, INVALID_TIME, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = String.format(INVALID_FIELD_MESSAGE_FORMAT, END_TIME_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, null, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, END_TIME_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, VALID_END_TIME, INVALID_DAY, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, VALID_END_TIME, null, VALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidNumberOfOccurrences_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, VALID_END_TIME, VALID_DAY, INVALID_NUMBER_OF_OCCURRENCES, VALID_VENUE);
        String expectedMessage = NumberOfOccurrences.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidVenue_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, VALID_END_TIME, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, INVALID_VENUE);
        String expectedMessage = Venue.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullVenue_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(
                VALID_START_TIME, VALID_END_TIME, VALID_DAY, VALID_NUMBER_OF_OCCURRENCES, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }
}
