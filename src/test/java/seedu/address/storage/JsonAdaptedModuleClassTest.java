package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedModuleClass.INVALID_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedModuleClass.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLesson.LESSON_WED_2_TO_4;
import static seedu.address.testutil.TypicalModuleClass.CS2103T_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.components.name.Name;
import seedu.address.model.lesson.Lesson;

public class JsonAdaptedModuleClassTest {

    private static final String INVALID_NAME = "CS3230@Tutorial";
    private static final String INVALID_STUDENT_UUID = "584346cb-8886-4518-8282-";
    private static final String INVALID_TIME = "1400";

    private static final JsonAdaptedName VALID_NAME = new JsonAdaptedName(CS2103T_TUTORIAL.getName().toString());
    private static final List<JsonAdaptedUuid> VALID_STUDENT_UUIDS = CS2103T_TUTORIAL.getStudentUuids().stream()
            .map(JsonAdaptedUuid::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedLesson> VALID_LESSONS = CS2103T_TUTORIAL.getLessons().stream()
            .map(JsonAdaptedLesson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validModuleClassDetails_returnsModuleClass() throws Exception {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(CS2103T_TUTORIAL);
        assertEquals(CS2103T_TUTORIAL, moduleClass.toModelType());
    }

    @Test
    public void toModelType_validIndividualModuleClassDetails_returnsModuleClass() throws Exception {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(
                VALID_NAME, VALID_STUDENT_UUIDS, VALID_LESSONS);
        assertEquals(CS2103T_TUTORIAL, moduleClass.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedName invalidName = new JsonAdaptedName(INVALID_NAME);
        JsonAdaptedModuleClass moduleClass =
                new JsonAdaptedModuleClass(invalidName, VALID_STUDENT_UUIDS, VALID_LESSONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(null, VALID_STUDENT_UUIDS, VALID_LESSONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_invalidStudentUuids_throwsIllegalValueException() {
        List<JsonAdaptedUuid> invalidStudentUuids = new ArrayList<>(VALID_STUDENT_UUIDS);
        JsonAdaptedUuid invalidJsonAdaptedUuid = new JsonAdaptedUuid(INVALID_STUDENT_UUID);
        invalidStudentUuids.add(invalidJsonAdaptedUuid);
        String expectedMessage = String.format(INVALID_FIELD_MESSAGE_FORMAT, JsonAdaptedModuleClass.STUDENT_UUID_FIELD);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(VALID_NAME,
                invalidStudentUuids,
                VALID_LESSONS);
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_nullStudentUuids_throwsIllegalValueException() {
        List<JsonAdaptedUuid> invalidStudentUuids = new ArrayList<>(VALID_STUDENT_UUIDS);
        invalidStudentUuids.add(null);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(
                VALID_NAME, invalidStudentUuids, VALID_LESSONS);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, JsonAdaptedModuleClass.STUDENT_UUID_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_invalidLessonStartTime_throwsIllegalValueException() {
        List<JsonAdaptedLesson> invalidLessons = new ArrayList<>(VALID_LESSONS);
        JsonAdaptedLesson invalidJsonAdaptedLesson = new JsonAdaptedLesson(
                INVALID_TIME,
                LESSON_WED_2_TO_4.getEndTime().toString(),
                LESSON_WED_2_TO_4.getDay().toString(),
                LESSON_WED_2_TO_4.getNumberOfOccurrences().value,
                LESSON_WED_2_TO_4.getVenue().toString()
        );
        invalidLessons.add(invalidJsonAdaptedLesson);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(
                VALID_NAME, VALID_STUDENT_UUIDS, invalidLessons);
        String expectedMessage = String.format(
                JsonAdaptedLesson.INVALID_FIELD_MESSAGE_FORMAT, JsonAdaptedLesson.START_TIME_FIELD);
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_nullLessons_throwsIllegalValueException() {
        List<JsonAdaptedLesson> invalidLessons = new ArrayList<>(VALID_LESSONS);
        invalidLessons.add(null);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(
                VALID_NAME, VALID_STUDENT_UUIDS, invalidLessons);
        String expectedMessage = String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Lesson.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }
}
