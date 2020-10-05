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

    @Test
    public void toModelType_typicalStudentsAndClassesFile_success() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(TYPICAL_STUDENTS_AND_CLASSES_FILE,
                JsonSerializableTutorsPet.class).get();
        TutorsPet tutorsPetFromFile = dataFromFile.toModelType();
        TutorsPet typicalStudentsTutorsPet = TypicalTutorsPet.getTypicalTutorsPet();

        assertEquals(tutorsPetFromFile, typicalStudentsTutorsPet);
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
     * Prevents Tutor's Pet from booting up if two or more students are found with the same UUID.
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
     * to pad missing UUID digits in broken UUIDs with zeros.
     */
    @Test
    public void toModelType_invalidStudentUuidInClassFile1_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(INVALID_STUDENT_UUID_IN_CLASS_FILE_1,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    /**
     * Ensures that Tutor's Pet will not be able to boot up given an incomplete UUID.
     * (e.g "0c527a3f-8a6f-4c16-b57d-")
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
     * If Tutor's Pet encounters duplicate student UUIDs in a class, it will not load
     * duplicate UUIDs into the model. Hence, Tutor's Pet should still boot up successfully.
     */
    @Test
    public void toModelType_duplicateStudentsInClassFile_success() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_UUID_IN_CLASS_FILE,
                JsonSerializableTutorsPet.class).get();
        TutorsPet tutorsPetFromFile = dataFromFile.toModelType();
        TutorsPet typicalStudentsTutorsPet = TypicalTutorsPet.getTypicalTutorsPet();

        assertEquals(tutorsPetFromFile, typicalStudentsTutorsPet);
    }
}
