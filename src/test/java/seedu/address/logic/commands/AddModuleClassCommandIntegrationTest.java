package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.testutil.ModuleClassBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddModuleClassCommand}.
 */
public class AddModuleClassCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_newModuleClass_success() {
        ModuleClass validModuleClass = new ModuleClassBuilder().build();

        String expectedMessage = String.format(AddModuleClassCommand.MESSAGE_SUCCESS, validModuleClass);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.addModuleClass(validModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(new AddModuleClassCommand(validModuleClass), model, expectedMessage, expectedModel);
        assertTrue(model.canUndo());
    }

    @Test
    public void execute_duplicateModuleClass_throwsCommandException() {
        ModuleClass moduleClassInList = model.getTutorsPet().getModuleClassList().get(0);
        assertCommandFailure(new AddModuleClassCommand(moduleClassInList), model,
                AddModuleClassCommand.MESSAGE_DUPLICATE_MODULE_CLASS);
        assertFalse(model.canUndo());
    }
}
