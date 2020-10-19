package tutorspet.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.TutorsPet;
import tutorspet.model.UserPrefs;
import tutorspet.model.student.Student;
import tutorspet.testutil.EditStudentDescriptorBuilder;
import tutorspet.testutil.StudentBuilder;
import tutorspet.testutil.TypicalIndexes;
import tutorspet.testutil.TypicalTutorsPet;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code EditStudentCommand}.
 */
public class EditStudentCommandTest {

    private Model model = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Student editedStudent = new StudentBuilder().build();
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(editedStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudent = Index.fromOneBased(model.getFilteredStudentList().size());
        Student lastStudent = model.getFilteredStudentList().get(indexLastStudent.getZeroBased());

        StudentBuilder studentInList = new StudentBuilder(lastStudent);
        Student editedStudent = studentInList.withName(CommandTestUtil.VALID_NAME_BOB)
                .withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB)
                .withTags(CommandTestUtil.VALID_TAG_AVERAGE).build();

        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder()
                .withName(CommandTestUtil.VALID_NAME_BOB).withTelegram(CommandTestUtil.VALID_TELEGRAM_BOB)
                .withTags(CommandTestUtil.VALID_TAG_AVERAGE).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(indexLastStudent, descriptor);

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setStudent(lastStudent, editedStudent);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditStudentCommand editStudentCommand =
                new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM, new EditStudentCommand.EditStudentDescriptor());
        Student editedStudent = model.getFilteredStudentList().get(TypicalIndexes.INDEX_FIRST_ITEM.getZeroBased());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        CommandTestUtil.showStudentAtIndex(model, TypicalIndexes.INDEX_FIRST_ITEM);

        Student studentInFilteredList = model.getFilteredStudentList()
                .get(TypicalIndexes.INDEX_FIRST_ITEM.getZeroBased());
        Student editedStudent = new StudentBuilder(studentInFilteredList)
                .withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM,
                new EditStudentDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditStudentCommand.MESSAGE_EDIT_STUDENT_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setStudent(model.getFilteredStudentList().get(0), editedStudent);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudentUnfilteredList_failure() {
        Student firstStudent = model.getFilteredStudentList().get(TypicalIndexes.INDEX_FIRST_ITEM.getZeroBased());
        EditStudentCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(firstStudent).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(TypicalIndexes.INDEX_SECOND_ITEM, descriptor);

        CommandTestUtil.assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_duplicateStudentFilteredList_failure() {
        CommandTestUtil.showStudentAtIndex(model, TypicalIndexes.INDEX_FIRST_ITEM);

        // edit student in filtered list into a duplicate in Tutor's Pet
        Student studentInList = model.getTutorsPet().getStudentList()
                .get(TypicalIndexes.INDEX_SECOND_ITEM.getZeroBased());
        EditStudentCommand editStudentCommand = new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM,
                new EditStudentDescriptorBuilder(studentInList).build());

        CommandTestUtil.assertCommandFailure(editStudentCommand, model, EditStudentCommand.MESSAGE_DUPLICATE_STUDENT);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        EditStudentCommand.EditStudentDescriptor descriptor =
                new EditStudentDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();
        EditStudentCommand editStudentCommand = new EditStudentCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editStudentCommand, model,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of student list.
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        CommandTestUtil.showStudentAtIndex(model, TypicalIndexes.INDEX_FIRST_ITEM);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_ITEM;
        // ensures that outOfBoundIndex is still in bounds of student list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorsPet().getStudentList().size());

        EditStudentCommand editStudentCommand = new EditStudentCommand(outOfBoundIndex,
                new EditStudentDescriptorBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build());

        CommandTestUtil.assertCommandFailure(editStudentCommand, model,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditStudentCommand standardCommand =
                new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM, CommandTestUtil.DESC_AMY);

        // same values -> returns true
        EditStudentCommand.EditStudentDescriptor copyDescriptor =
                new EditStudentCommand.EditStudentDescriptor(CommandTestUtil.DESC_AMY);
        EditStudentCommand commandWithSameValues =
                new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(TypicalIndexes.INDEX_SECOND_ITEM,
                CommandTestUtil.DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditStudentCommand(TypicalIndexes.INDEX_FIRST_ITEM,
                CommandTestUtil.DESC_BOB)));
    }
}
