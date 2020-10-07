package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) for {@code UnlinkCommand}.
 */
public class UnlinkCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullStudentIndex_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(INDEX_FIRST_ITEM, null));
    }

    @Test
    public void constructor_nullModuleClassIndex_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(null, INDEX_FIRST_ITEM));
    }

    @Test
    public void execute_unfilteredList_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        // manually unlink first student from first class
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        ModuleClass modifiedModuleClass = manualUnlinkStudentFromModuleClass(moduleClass, student);

        Model expectedModel = copyModelWithModuleClassAndShowStudents(model, moduleClass, modifiedModuleClass);

        assertCommandSuccess(new UnlinkCommand(moduleClassIndex, studentIndex), model,
                String.format(UnlinkCommand.MESSAGE_UNLINK_SUCCESS, student.getName(), modifiedModuleClass),
                expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        showModuleClassAtIndex(model, INDEX_THIRD_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        // manually unlink first student from first class
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        ModuleClass modifiedModuleClass = manualUnlinkStudentFromModuleClass(moduleClass, student);

        Model expectedModel = copyModelWithModuleClassAndShowStudents(model, moduleClass, modifiedModuleClass);

        assertCommandSuccess(new UnlinkCommand(moduleClassIndex, studentIndex), model,
                String.format(UnlinkCommand.MESSAGE_UNLINK_SUCCESS, student.getName(), modifiedModuleClass),
                expectedModel);
    }

    @Test
    public void execute_studentNotInModuleClass_failure() {
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_ITEM, INDEX_THIRD_ITEM);

        assertCommandFailure(unlinkCommand, model, UnlinkCommand.MESSAGE_MISSING_LINK);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_ITEM, outOfBoundIndex);

        assertCommandFailure(unlinkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of student list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getStudentList().size());

        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_ITEM, outOfBoundIndex);

        assertCommandFailure(unlinkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidModuleClassIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        UnlinkCommand unlinkCommand = new UnlinkCommand(outOfBoundIndex, INDEX_FIRST_ITEM);

        assertCommandFailure(unlinkCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidModuleClassIndexFilteredList_failure() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getModuleClassList().size());

        UnlinkCommand unlinkCommand = new UnlinkCommand(outOfBoundIndex, INDEX_FIRST_ITEM);

        assertCommandFailure(unlinkCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnlinkCommand unlinkCommand = new UnlinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);

        // same indexes -> return true
        UnlinkCommand unlinkCommandCopy = new UnlinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        assertTrue(unlinkCommand.equals(unlinkCommandCopy));

        // same object -> returns true
        assertTrue(unlinkCommand.equals(unlinkCommand));

        // null -> returns false
        assertFalse(unlinkCommand.equals(null));

        // different type -> returns false
        assertFalse(unlinkCommand.equals(5));

        // different student index -> returns false
        UnlinkCommand unlinkCommandDifferentStudent = new UnlinkCommand(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM);
        assertFalse(unlinkCommand.equals(unlinkCommandDifferentStudent));

        // different class index -> returns false
        UnlinkCommand unlinkCommandDifferentClass = new UnlinkCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM);
        assertFalse(unlinkCommand.equals(unlinkCommandDifferentClass));
    }

    /**
     * Returns a new {@code Model} based on the given {@code model} where {@code toReplace} has been replaced with
     * {@code toSet} and the {@code filteredModuleClasses} list shows only {@code toSet} while the
     * {@code filteredStudents} list shows only students in {@code toSet}.
     */
    public static Model copyModelWithModuleClassAndShowStudents(Model model, ModuleClass toReplace, ModuleClass toSet) {
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        Set<UUID> studentUuids = toSet.getStudentUuids();

        expectedModel.setModuleClass(toReplace, toSet);
        expectedModel.updateFilteredStudentList(s -> studentUuids.contains(s.getUuid()));
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(toSet));
        return expectedModel;
    }

    /**
     * Returns a new {@code ModuleClass} based on the given {@code moduleClass} but with the specified {@code student}
     * removed.
     * Requires {@code student} to be linked to {@code moduleClass}.
     */
    private static ModuleClass manualUnlinkStudentFromModuleClass(ModuleClass moduleClass, Student student) {
        Set<UUID> studentUuids = new HashSet<>(moduleClass.getStudentUuids());

        assertTrue(studentUuids.contains(student.getUuid()), "Test precondition error: The selected module class"
                        + " does not contain the selected student.");

        studentUuids.remove(student.getUuid());
        return new ModuleClass(moduleClass.getName(), studentUuids);
    }
}
