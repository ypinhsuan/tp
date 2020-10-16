package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.components.name.Name;

public class JsonAdaptedNameTest {

    public static final JsonAdaptedName VALID_JSON_ADAPTED_NAME = new JsonAdaptedName(VALID_NAME_AMY);

    @Test
    public void toModelType_validName_returnsName() throws Exception {
        JsonAdaptedName validName = new JsonAdaptedName(VALID_NAME_AMY);
        assertEquals(new Name(VALID_NAME_AMY), validName.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedName invalidName = new JsonAdaptedName(INVALID_NAME);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, invalidName::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedName nullName = new JsonAdaptedName((String) null);
        assertThrows(IllegalValueException.class, Name.MESSAGE_CONSTRAINTS, nullName::toModelType);
    }
}
