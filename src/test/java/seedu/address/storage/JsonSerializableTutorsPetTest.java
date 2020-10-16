package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.TutorsPet;
import seedu.address.testutil.TypicalTutorsPet;

public class JsonSerializableTutorsPetTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableTutorsPetTest");
    private static final Path STUDENT_TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableTutorsPetTest", "Student");
    private static final Path CLASS_TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableTutorsPetTest", "Class");
    private static final Path LESSON_TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableTutorsPetTest", "Lesson");

    private static final Path TYPICAL_STUDENTS_AND_CLASSES_FILE =
            TEST_DATA_FOLDER.resolve("typicalStudentsAndClassesTutorsPet.json");

    private static final Path INVALID_STUDENT_FILE = STUDENT_TEST_DATA_FOLDER.resolve("invalidStudentTutorsPet.json");
    private static final Path DUPLICATE_STUDENT_NAME_AND_TELEGRAM_FILE =
            STUDENT_TEST_DATA_FOLDER.resolve("duplicateStudentNameAndTelegramTutorsPet.json");
    private static final Path DUPLICATE_STUDENT_NAME_AND_EMAIL_FILE =
            STUDENT_TEST_DATA_FOLDER.resolve("duplicateStudentNameAndEmailTutorsPet.json");
    private static final Path DUPLICATE_STUDENT_UUID_FILE =
            STUDENT_TEST_DATA_FOLDER.resolve("duplicateStudentUuidTutorsPet.json");

    private static final Path INVALID_CLASS_FILE = CLASS_TEST_DATA_FOLDER.resolve("invalidClassTutorsPet.json");
    private static final Path INVALID_STUDENT_UUID_IN_CLASS_FILE_1 =
            CLASS_TEST_DATA_FOLDER.resolve("invalidStudentUuidInClass1.json");
    private static final Path INVALID_STUDENT_UUID_IN_CLASS_FILE_2 =
            CLASS_TEST_DATA_FOLDER.resolve("invalidStudentUuidInClass2.json");
    private static final Path DUPLICATE_CLASS_FILE = CLASS_TEST_DATA_FOLDER.resolve("duplicateClassTutorsPet.json");
    private static final Path DUPLICATE_STUDENT_UUID_IN_CLASS_FILE =
            CLASS_TEST_DATA_FOLDER.resolve("duplicateStudentUuidInClass.json");

    private static final Path DUPLICATE_LESSON_FILE =
            LESSON_TEST_DATA_FOLDER.resolve("duplicateLessonTutorsPet.json");
    private static final Path INVALID_LESSON_FILE =
            LESSON_TEST_DATA_FOLDER.resolve("invalidLessonTutorsPet.json");
    private static final Path NULL_LESSON_FILE =
            LESSON_TEST_DATA_FOLDER.resolve("nullLessonTutorsPet.json");

    @Test
    public void toModelType_typicalStudentsAndClassesFile_success() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_AND_CLASSES_FILE,
                JsonSerializableTutorsPet.class).get();
        TutorsPet tutorsPetFromFile = dataFromFile.toModelType();
        TutorsPet typicalStudentsTutorsPet = TypicalTutorsPet.getTypicalTutorsPet();
        assertEquals(typicalStudentsTutorsPet, tutorsPetFromFile);
    }

    @Test
    public void toModelType_invalidStudentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudentsNameAndTelegram_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_NAME_AND_TELEGRAM_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTutorsPet.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateStudentsNameAndEmail_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_NAME_AND_EMAIL_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTutorsPet.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up if two or more {@code Student}s
     * are found with the same {@code UUID}.
     */
    @Test
    public void toModelType_duplicateStudentsUuid_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_UUID_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableTutorsPet.MESSAGE_DUPLICATE_STUDENT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidClassFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_CLASS_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up although Java tries
     * to pad missing {@code UUID} digits in broken {@code UUID}s with zeros.
     */
    @Test
    public void toModelType_invalidStudentUuidInClassFile1_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_UUID_IN_CLASS_FILE_1,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up given an incomplete {@code UUID}.
     * (e.g "0c527a3f-8a6f-4c16-b57d-").
     */
    @Test
    public void toModelType_invalidStudentUuidInClassFile2_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_UUID_IN_CLASS_FILE_2,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClasses_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLASS_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * If Tutor's Pet encounters duplicate {@code Student UUID}s in a class, it will not load
     * duplicate {@code UUID}s into the model. Tutor's Pet should still boot up successfully.
     */
    @Test
    public void toModelType_duplicateStudentsInClassFile_success() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_UUID_IN_CLASS_FILE,
                JsonSerializableTutorsPet.class).get();
        TutorsPet tutorsPetFromFile = dataFromFile.toModelType();
        TutorsPet typicalStudentsTutorsPet = TypicalTutorsPet.getTypicalTutorsPet();
        assertEquals(tutorsPetFromFile, typicalStudentsTutorsPet);
    }

    @Test
    public void toModelType_invalidLessonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_LESSON_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up given duplicate {@code Lesson}s.
     */
    @Test
    public void toModelType_duplicateLesson_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_LESSON_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up when there exists null {@code Lesson}s.
     */
    @Test
    public void toModelType_nullLesson_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(NULL_LESSON_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
