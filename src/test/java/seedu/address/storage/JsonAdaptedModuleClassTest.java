package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedModuleClass.INVALID_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedModuleClass.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModuleClass.CS2103T_TUTORIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.components.Name;

public class JsonAdaptedModuleClassTest {

    private static final String INVALID_NAME = "CS3230@Tutorial";
    private static final String INVALID_STUDENT_UUID = "584346cb-8886-4518-8282-";

    private static final String VALID_NAME = CS2103T_TUTORIAL.getName().toString();
    private static final List<JsonAdaptedUuid> VALID_STUDENT_UUIDS = CS2103T_TUTORIAL.getStudentUuids().stream()
            .map(JsonAdaptedUuid::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validModuleClassDetails_returnsModuleClass() throws Exception {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(CS2103T_TUTORIAL);
        assertEquals(CS2103T_TUTORIAL, moduleClass.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(INVALID_NAME, VALID_STUDENT_UUIDS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(null, VALID_STUDENT_UUIDS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_invalidStudentUuids_throwsIllegalValueException() {
        List<JsonAdaptedUuid> invalidStudentUuids = new ArrayList<>(VALID_STUDENT_UUIDS);
        JsonAdaptedUuid invalidJsonAdaptedUuid = new JsonAdaptedUuid(INVALID_STUDENT_UUID);
        invalidStudentUuids.add(invalidJsonAdaptedUuid);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(VALID_NAME, invalidStudentUuids);
        String expectedMessage = String.format(INVALID_FIELD_MESSAGE_FORMAT, "studentUuid");
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }

    @Test
    public void toModelType_nullStudentUuids_throwsIllegalValueException() {
        List<JsonAdaptedUuid> invalidStudentUuids = new ArrayList<>(VALID_STUDENT_UUIDS);
        invalidStudentUuids.add(null);
        JsonAdaptedModuleClass moduleClass = new JsonAdaptedModuleClass(VALID_NAME, invalidStudentUuids);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "studentUuid");
        assertThrows(IllegalValueException.class, expectedMessage, moduleClass::toModelType);
    }
}
