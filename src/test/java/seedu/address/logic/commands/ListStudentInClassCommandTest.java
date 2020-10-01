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

public class ListStudentInClassCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_indexSpecifiedListsNotFiltered_showsStudentsInClass() {
        ListStudentInClassCommand listStudentInClassCommand = new ListStudentInClassCommand(INDEX_FIRST_ITEM);

        ModuleClass selectedClass = showStudentsInModuleClassAtIndex(expectedModel, INDEX_FIRST_ITEM);

        assertCommandSuccess(listStudentInClassCommand, model,
                String.format(ListStudentInClassCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, selectedClass),
                expectedModel);
    }

    @Test
    public void execute_indexSpecifiedStudentListIsFiltered_showsStudentsInClass() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        ListStudentInClassCommand listStudentInClassCommand = new ListStudentInClassCommand(INDEX_FIRST_ITEM);

        ModuleClass selectedClass = showStudentsInModuleClassAtIndex(expectedModel, INDEX_FIRST_ITEM);

        assertCommandSuccess(listStudentInClassCommand, model,
                String.format(ListStudentInClassCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, selectedClass),
                expectedModel);
    }

    @Test
    public void execute_indexSpecifiedClassListIsFiltered_showsStudentsInClass() {
        showModuleClassAtIndex(model, INDEX_SECOND_ITEM);
        showModuleClassAtIndex(expectedModel, INDEX_SECOND_ITEM);
        ListStudentInClassCommand listStudentInClassCommand = new ListStudentInClassCommand(INDEX_FIRST_ITEM);

        ModuleClass specifiedClass = expectedModel.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Collection<UUID> expectedStudents = specifiedClass.getStudentUuids();
        expectedModel.updateFilteredStudentList(new StudentInUuidCollectionPredicate(expectedStudents));

        assertCommandSuccess(listStudentInClassCommand, model,
                String.format(ListStudentInClassCommand.MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, specifiedClass),
                expectedModel);
    }

    @Test
    public void execute_invalidIndexSpecifiedClassListIsNotFiltered_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        ListStudentInClassCommand listStudentInClassCommand = new ListStudentInClassCommand(outOfBoundIndex);

        assertCommandFailure(listStudentInClassCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexSpecifiedClassListIsFiltered_throwsCommandException() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of the class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getModuleClassList().size());

        ListStudentInClassCommand listStudentInClassCommand = new ListStudentInClassCommand(outOfBoundIndex);

        assertCommandFailure(listStudentInClassCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListStudentInClassCommand listClassOneCommand = new ListStudentInClassCommand(INDEX_FIRST_ITEM);
        ListStudentInClassCommand listClassTwoCommand = new ListStudentInClassCommand(INDEX_SECOND_ITEM);

        // same class -> returns true
        ListStudentInClassCommand listClassOneCommandCopy = new ListStudentInClassCommand(INDEX_FIRST_ITEM);
        assertTrue(listClassOneCommand.equals(listClassOneCommandCopy));

        // same object -> returns true
        assertTrue(listClassOneCommand.equals(listClassOneCommand));

        // null -> returns false
        assertFalse(listClassOneCommand.equals(null));

        // different type -> returns false
        assertFalse(listClassOneCommand.equals(5));

        // different class -> returns false
        assertFalse(listClassOneCommand.equals(listClassTwoCommand));
    }

    /**
     * Updates {@code model}'s {@code filteredStudents} list to show only the students in the {@code ModuleClass}
     * as specified by the given {@code moduleClassIndex} in the {@code model}'s displayed class list.
     */
    private ModuleClass showStudentsInModuleClassAtIndex(Model model, Index moduleClassIndex) {
        ModuleClass selectedClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Collection<UUID> expectedStudents = selectedClass.getStudentUuids();
        model.updateFilteredStudentList(new StudentInUuidCollectionPredicate(expectedStudents));
        return selectedClass;
    }
}
