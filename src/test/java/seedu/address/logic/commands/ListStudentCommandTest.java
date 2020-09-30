package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.Collection;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.StudentInUuidCollectionPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListStudentCommand.
 */
public class ListStudentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_noIndexStudentListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListStudentCommand(), model,
                ListStudentCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noIndexStudentListIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ListStudentCommand(), model,
                ListStudentCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_indexSpecifiedUnfilteredLists_showsStudentsInClass() {
        ListStudentCommand listStudentCommand = new ListStudentCommand(INDEX_FIRST_ITEM);

        ModuleClass specifiedClass = expectedModel.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Collection<UUID> expectedStudents = specifiedClass.getStudentUuids();
        expectedModel.updateFilteredStudentList(new StudentInUuidCollectionPredicate(expectedStudents));

        assertCommandSuccess(listStudentCommand, model,
                String.format(ListStudentCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, specifiedClass), expectedModel);
    }

    @Test
    public void execute_indexSpecifiedStudentListIsFiltered_showsStudentsInClass() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        ListStudentCommand listStudentCommand = new ListStudentCommand(INDEX_FIRST_ITEM);

        ModuleClass specifiedClass = expectedModel.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Collection<UUID> expectedStudents = specifiedClass.getStudentUuids();
        expectedModel.updateFilteredStudentList(new StudentInUuidCollectionPredicate(expectedStudents));

        assertCommandSuccess(listStudentCommand, model,
                String.format(ListStudentCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, specifiedClass), expectedModel);
    }

    @Test
    public void execute_indexSpecifiedClassListIsFiltered_showsStudentsInClass() {
        showModuleClassAtIndex(model, INDEX_SECOND_ITEM);
        showModuleClassAtIndex(expectedModel, INDEX_SECOND_ITEM);
        ListStudentCommand listStudentCommand = new ListStudentCommand(INDEX_FIRST_ITEM);

        ModuleClass specifiedClass = expectedModel.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Collection<UUID> expectedStudents = specifiedClass.getStudentUuids();
        expectedModel.updateFilteredStudentList(new StudentInUuidCollectionPredicate(expectedStudents));

        assertCommandSuccess(listStudentCommand, model,
                String.format(ListStudentCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, specifiedClass), expectedModel);
    }

    @Test
    public void execute_invalidIndexSpecifiedUnfilteredClassList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        ListStudentCommand listStudentCommand = new ListStudentCommand(outOfBoundIndex);

        assertCommandFailure(listStudentCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexSpecifiedFilteredClassList_throwsCommandException() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of the class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getModuleClassList().size());

        ListStudentCommand listStudentCommand = new ListStudentCommand(outOfBoundIndex);

        assertCommandFailure(listStudentCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListStudentCommand listAllCommand = new ListStudentCommand();
        ListStudentCommand listClassOneCommand = new ListStudentCommand(INDEX_FIRST_ITEM);
        ListStudentCommand listClassTwoCommand = new ListStudentCommand(INDEX_SECOND_ITEM);

        // same class -> returns true
        ListStudentCommand listClassOneCommandCopy = new ListStudentCommand(INDEX_FIRST_ITEM);
        assertTrue(listClassOneCommand.equals(listClassOneCommandCopy));

        // same object -> returns true
        assertTrue(listAllCommand.equals(listAllCommand));

        // null -> returns false
        assertFalse(listAllCommand.equals(null));

        // different type -> returns false
        assertFalse(listAllCommand.equals(5));

        // different class -> returns false
        assertFalse(listClassOneCommand.equals(listClassTwoCommand));

        // no class and specified class -> returns false
        assertFalse(listAllCommand.equals(listClassOneCommand));
    }
}
