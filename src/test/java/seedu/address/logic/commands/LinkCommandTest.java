package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULE_CLASS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getNoLinkTutorsPet;

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
 * Contains integration tests (interaction with the Model) for {@code LinkCommand}.
 */
public class LinkCommandTest {

    private Model model = new ModelManager(getNoLinkTutorsPet(), new UserPrefs());

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
        // manually link first class to first student
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(0);
        Student student = model.getFilteredStudentList().get(0);
        Set<UUID> studentUuids = new HashSet<>(moduleClass.getStudentUuids());
        studentUuids.add(student.getUuid());
        ModuleClass modifiedModuleClass = new ModuleClass(moduleClass.getName(), studentUuids);

        // set up expected model with linked class and update filtered lists
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.updateFilteredStudentList(s -> studentUuids.contains(s.getUuid()));
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(modifiedModuleClass));

        assertCommandSuccess(new LinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM), model,
                String.format(LinkCommand.MESSAGE_LINK_SUCCESS, student.getName(), modifiedModuleClass), expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        // manually link first class to first student
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(0);
        Student student = model.getFilteredStudentList().get(0);
        Set<UUID> studentUuids = new HashSet<>(moduleClass.getStudentUuids());
        studentUuids.add(student.getUuid());
        ModuleClass modifiedModuleClass = new ModuleClass(moduleClass.getName(), studentUuids);

        // set up expected model with linked class and update filtered lists
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.updateFilteredStudentList(s -> studentUuids.contains(s.getUuid()));
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(modifiedModuleClass));

        assertCommandSuccess(new LinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM), model,
                String.format(LinkCommand.MESSAGE_LINK_SUCCESS, student.getName(), modifiedModuleClass), expectedModel);
    }

    @Test
    public void execute_existingStudent_failure() {
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(0);
        Student student = model.getFilteredStudentList().get(0);
        Set<UUID> studentUuids = new HashSet<>(moduleClass.getStudentUuids());
        studentUuids.add(student.getUuid());
        ModuleClass modifiedModuleClass = new ModuleClass(moduleClass.getName(), studentUuids);
        model.setModuleClass(moduleClass, modifiedModuleClass);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredModuleClassList(PREDICATE_SHOW_ALL_MODULE_CLASS);

        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);

        assertCommandFailure(linkCommand, model, LinkCommand.MESSAGE_EXISTING_LINK);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_ITEM, outOfBoundIndex);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of student list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getStudentList().size());

        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_ITEM, outOfBoundIndex);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidModuleClassIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        LinkCommand linkCommand = new LinkCommand(outOfBoundIndex, INDEX_FIRST_ITEM);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidModuleClassIndexFilteredList_failure() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        Index outOfBoundIndex = INDEX_SECOND_ITEM;

        // ensures that outOfBoundIndex is still in bounds of class list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getModuleClassList().size());

        LinkCommand linkCommand = new LinkCommand(outOfBoundIndex, INDEX_FIRST_ITEM);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LinkCommand linkCommand = new LinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);

        // same indexes -> return true
        LinkCommand linkCommandCopy = new LinkCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        assertTrue(linkCommand.equals(linkCommandCopy));

        // same object -> returns true
        assertTrue(linkCommand.equals(linkCommand));

        // null -> returns false
        assertFalse(linkCommand.equals(null));

        // different type -> returns false
        assertFalse(linkCommand.equals(5));

        // different student index -> returns false
        LinkCommand linkCommandDifferentStudent = new LinkCommand(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM);
        assertFalse(linkCommand.equals(linkCommandDifferentStudent));

        // different class index -> returns false
        LinkCommand linkCommandDifferentClass = new LinkCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM);
        assertFalse(linkCommand.equals(linkCommandDifferentClass));
    }
}
