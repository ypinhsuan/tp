package tutorspet.logic.commands.attendance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_LINK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.logic.commands.CommandTestUtil.DESC_ATTENDANCE_33;
import static tutorspet.logic.commands.CommandTestUtil.DESC_ATTENDANCE_80;
import static tutorspet.logic.commands.CommandTestUtil.INVALID_PARTICIPATION_SCORE_101;
import static tutorspet.logic.commands.CommandTestUtil.VALID_ATTENDANCE_51;
import static tutorspet.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static tutorspet.logic.commands.CommandTestUtil.showStudentAtIndex;
import static tutorspet.logic.commands.attendance.EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS;
import static tutorspet.logic.util.ModuleClassUtil.editAttendanceInModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getAttendanceFromModuleClass;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.attendance.EditAttendanceCommand.EditAttendanceDescriptor;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.TutorsPet;
import tutorspet.model.UserPrefs;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;
import tutorspet.testutil.EditAttendanceDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditAttendanceCommand}.
 */
public class EditAttendanceCommandTest {

    private static final EditAttendanceDescriptor EDITED_ATTENDANCE_DESCRIPTOR =
            new EditAttendanceDescriptorBuilder(VALID_ATTENDANCE_51).build();

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullModuleClassIndex_throwsNullPointerException() {
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                null, lessonIndex, studentIndex, targetWeek, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullLessonIndex_throwsNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                moduleClassIndex, null, studentIndex, targetWeek, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, null, targetWeek, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, null, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        ModuleClass editedModuleClass =
                editAttendanceInModuleClass(moduleClass, lessonIndex, targetWeek, student, editedAttendance);

        String expectedMessage = String.format(MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), targetWeek, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws CommandException {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                studentIndex, targetWeek, new EditAttendanceDescriptor());

        Student student = model.getFilteredStudentList().get(moduleClassIndex.getZeroBased());
        ModuleClass targetModuleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Attendance editedAttendance = getAttendanceFromModuleClass(
                targetModuleClass, lessonIndex, targetWeek, student);

        String expectedMessage = String.format(MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), targetWeek, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws CommandException {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        showStudentAtIndex(model, INDEX_FIRST_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        ModuleClass moduleClassInFilteredList = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student studentInFilteredList = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);

        ModuleClass editedModuleClass = editAttendanceInModuleClass(moduleClassInFilteredList, lessonIndex,
                targetWeek, studentInFilteredList, editedAttendance);

        String expectedMessage = String.format(MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                studentInFilteredList.getName(), targetWeek, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.updateFilteredStudentList(s -> s.equals(studentInFilteredList));
        expectedModel.setModuleClass(moduleClassInFilteredList, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                outOfBoundIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);

        EditAttendanceDescriptor editAttendanceDescriptor = new EditAttendanceDescriptorBuilder()
                .withParticipationScore(INVALID_PARTICIPATION_SCORE_101).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, outOfBoundIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, outOfBoundIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidWeek_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        Lesson lesson = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased())
                .getLessons().get(lessonIndex.getZeroBased());
        Week invalidWeek =
                new Week(Index.fromOneBased(lesson.getAttendanceRecordList().getAttendanceRecordList().size() + 1));

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, invalidWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_INVALID_WEEK);
    }

    @Test
    public void execute_studentNotInClass_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                INDEX_THIRD_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_MISSING_LINK);
    }

    @Test
    public void execute_attendanceDoesNotExist_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                studentIndex, VALID_WEEK_5, DESC_ATTENDANCE_33);

        assertCommandFailure(editAttendanceCommand, model, MESSAGE_MISSING_STUDENT_ATTENDANCE);
    }

    @Test
    public void equals() {
        final EditAttendanceCommand standardCommand = new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33);

        // same values -> returns true
        EditAttendanceDescriptor copyDescriptor = new EditAttendanceDescriptor(DESC_ATTENDANCE_33);
        EditAttendanceCommand commandWithSameValues = new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // different module class index -> returns false
        assertFalse(standardCommand.equals(new EditAttendanceCommand(INDEX_SECOND_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33)));

        // different lesson index -> returns false
        assertFalse(standardCommand.equals(new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_SECOND_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33)));

        // different student index -> returns false
        assertFalse(standardCommand.equals(new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_SECOND_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33)));

        // different week -> returns false
        assertFalse(standardCommand.equals(new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_5, DESC_ATTENDANCE_33)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_80)));
    }
}
