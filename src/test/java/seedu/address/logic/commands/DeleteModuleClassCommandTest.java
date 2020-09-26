package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteModuleClassCommand}.
 */
public class DeleteModuleClassCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ModuleClass moduleClassToDelete = model.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteModuleClassCommand deleteModuleClassCommand = new DeleteModuleClassCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteModuleClassCommand.MESSAGE_DELETE_MODULE_CLASS_SUCCESS,
                moduleClassToDelete);

        ModelManager expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.deleteModuleClass(moduleClassToDelete);

        assertCommandSuccess(deleteModuleClassCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        DeleteModuleClassCommand deleteModuleClassCommand = new DeleteModuleClassCommand(outOfBoundIndex);

        assertCommandFailure(deleteModuleClassCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        ModuleClass moduleClassToDelete = model.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteModuleClassCommand deleteModuleClassCommand = new DeleteModuleClassCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(DeleteModuleClassCommand.MESSAGE_DELETE_MODULE_CLASS_SUCCESS,
                moduleClassToDelete);

        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.deleteModuleClass(moduleClassToDelete);
        showNoModuleClass(expectedModel);

        assertCommandSuccess(deleteModuleClassCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of Tutor's Pet list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getModuleClassList().size());

        DeleteModuleClassCommand deleteModuleClassCommand = new DeleteModuleClassCommand(outOfBoundIndex);

        assertCommandFailure(deleteModuleClassCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteModuleClassCommand deleteFirstModuleClassCommand = new DeleteModuleClassCommand(INDEX_FIRST_ITEM);
        DeleteModuleClassCommand deleteSecondModuleClassCommand = new DeleteModuleClassCommand(INDEX_SECOND_ITEM);

        // same object -> returns true
        assertTrue(deleteFirstModuleClassCommand.equals(deleteFirstModuleClassCommand));

        // same values -> returns true
        DeleteModuleClassCommand deleteFirstModuleClassCommandCopy = new DeleteModuleClassCommand(INDEX_FIRST_ITEM);
        assertTrue(deleteFirstModuleClassCommand.equals(deleteFirstModuleClassCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstModuleClassCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstModuleClassCommand.equals(null));

        // different moduleClass -> returns false
        assertFalse(deleteFirstModuleClassCommand.equals(deleteSecondModuleClassCommand));
    }

    /**
     * Updates {@code model}'s {@code filteredModuleClasses} list to show no module classes.
     */
    private void showNoModuleClass(Model model) {
        model.updateFilteredModuleClassList(p -> false);

        assertTrue(model.getFilteredModuleClassList().isEmpty());
    }
}
