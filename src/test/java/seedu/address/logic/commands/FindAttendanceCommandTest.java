package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * Contains integration tests (interaction with the Model) for {@code FindAttendanceCommand}.
 */
public class FindAttendanceCommandTest {

    private final Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FindAttendanceCommand(null, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1));
        assertThrows(NullPointerException.class, () ->
                new FindAttendanceCommand(INDEX_FIRST_ITEM, null, INDEX_FIRST_ITEM, VALID_WEEK_1));
        assertThrows(NullPointerException.class, () ->
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null, VALID_WEEK_1));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        Attendance attendance = lesson.getAttendanceRecordList().getAttendance(student, targetWeek);
        String expectedMessage = String.format(FindAttendanceCommand.MESSAGE_SUCCESS, student.getName(),
                targetWeek, attendance);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandSuccess(findAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentNotInClass_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        FindAttendanceCommand findAttendanceCommand = new FindAttendanceCommand(moduleClassIndex, lessonIndex,
                INDEX_THIRD_ITEM, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
    }

    @Test
    public void execute_invalidClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(outOfBoundIndex, lessonIndex, studentIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, outOfBoundIndex, studentIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        FindAttendanceCommand findAttendanceCommand = new FindAttendanceCommand(moduleClassIndex,
                lessonIndex, outOfBoundIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, invalidWeek);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_INVALID_WEEK);
    }

    @Test
    public void execute_noStudentAttendance_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week week = VALID_WEEK_5;

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week);

        assertCommandFailure(findAttendanceCommand, model, Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE);
    }

    @Test
    public void equals() {
        Week week1 = new Week(Index.fromOneBased(1));
        Week week2 = new Week(Index.fromOneBased(2));
        FindAttendanceCommand findAttendanceCommand = new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, week1);

        // same object -> returns true
        assertTrue(findAttendanceCommand.equals(findAttendanceCommand));

        // same value -> returns true
        FindAttendanceCommand duplicateFindAttendanceCommand = new FindAttendanceCommand(INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, week1);
        assertTrue(findAttendanceCommand.equals(findAttendanceCommand));

        // different type -> returns false
        assertFalse(findAttendanceCommand.equals(5));

        // null -> returns false
        assertFalse(findAttendanceCommand.equals(null));

        // different weeks -> return false
        FindAttendanceCommand findAttendanceCommandDifferentWeek =
                new FindAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, week2);
        assertFalse(findAttendanceCommand.equals(findAttendanceCommandDifferentWeek));
    }
}
