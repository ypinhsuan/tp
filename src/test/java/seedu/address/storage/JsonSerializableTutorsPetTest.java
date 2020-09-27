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

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTutorsPetTest");
    private static final Path TYPICAL_STUDENTS_AND_CLASSES_FILE =
            TEST_DATA_FOLDER.resolve("typicalStudentsAndClassesTutorsPet.json");
    private static final Path INVALID_STUDENT_FILE = TEST_DATA_FOLDER.resolve("invalidStudentTutorsPet.json");
    private static final Path DUPLICATE_STUDENT_FILE = TEST_DATA_FOLDER.resolve("duplicateStudentTutorsPet.json");
    private static final Path INVALID_CLASS_FILE = TEST_DATA_FOLDER.resolve("invalidClassTutorsPet.json");
    private static final Path DUPLICATE_CLASS_FILE = TEST_DATA_FOLDER.resolve("duplicateClassTutorsPet.json");

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
    public void toModelType_duplicateStudents_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STUDENT_FILE,
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

    @Test
    public void toModelType_duplicateClasses_throwsIllegalValueException() throws Exception {
        JsonSerializableTutorsPet dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLASS_FILE,
                JsonSerializableTutorsPet.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
