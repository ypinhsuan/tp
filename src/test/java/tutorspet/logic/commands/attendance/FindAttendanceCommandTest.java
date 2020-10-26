package tutorspet.logic.commands.attendance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_WEEK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_LINK;
import static tutorspet.commons.core.Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static tutorspet.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static tutorspet.logic.commands.CommandTestUtil.showStudentAtIndex;
import static tutorspet.logic.commands.attendance.FindAttendanceCommand.MESSAGE_SUCCESS;
import static tutorspet.logic.util.ModuleClassUtil.getAttendanceFromModuleClass;
import static tutorspet.logic.util.ModuleClassUtil.getLessonFromModuleClass;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;

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
    public void execute_unfilteredList_success() throws CommandException {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = getLessonFromModuleClass(moduleClass, lessonIndex);
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());

        Attendance attendance = getAttendanceFromModuleClass(moduleClass, lessonIndex, targetWeek, student);

        String expectedMessage = String.format(MESSAGE_SUCCESS,
                student.getName(), moduleClass.getName(), lesson, targetWeek, attendance);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandSuccess(findAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws CommandException {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        showStudentAtIndex(model, INDEX_FIRST_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = getLessonFromModuleClass(moduleClass, lessonIndex);
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());

        Attendance attendance = getAttendanceFromModuleClass(moduleClass, lessonIndex, targetWeek, student);
        String expectedMessage = String.format(MESSAGE_SUCCESS,
                student.getName(), moduleClass.getName(), lesson, targetWeek, attendance);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(moduleClass));
        expectedModel.updateFilteredStudentList(s -> s.isSameStudent(student));

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandSuccess(findAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentNotInClass_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());

        assert lessonIndex.getZeroBased() < moduleClass.getLessons().size();

        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        assert moduleClass.hasLesson(lesson);

        FindAttendanceCommand findAttendanceCommand = new FindAttendanceCommand(moduleClassIndex, lessonIndex,
                INDEX_THIRD_ITEM, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_MISSING_LINK);
    }

    @Test
    public void execute_invalidClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(outOfBoundIndex, lessonIndex, studentIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, outOfBoundIndex, studentIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        FindAttendanceCommand findAttendanceCommand = new FindAttendanceCommand(moduleClassIndex,
                lessonIndex, outOfBoundIndex, VALID_WEEK_1);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidWeek_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());

        assert lessonIndex.getZeroBased() < moduleClass.getLessons().size();

        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        assert moduleClass.hasLesson(lesson);

        Week invalidWeek =
                new Week(Index.fromOneBased(lesson.getAttendanceRecordList().getAttendanceRecordList().size() + 1));

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, invalidWeek);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_INVALID_WEEK);
    }

    @Test
    public void execute_noStudentAttendance_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week week = VALID_WEEK_5;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();
        assert studentIndex.getZeroBased() < model.getFilteredStudentList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());

        assert lessonIndex.getZeroBased() < moduleClass.getLessons().size();

        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        assert moduleClass.hasLesson(lesson);
        assert moduleClass.hasStudentUuid(student.getUuid());

        FindAttendanceCommand findAttendanceCommand =
                new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week);

        assertCommandFailure(findAttendanceCommand, model, MESSAGE_MISSING_STUDENT_ATTENDANCE);
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
        assertTrue(findAttendanceCommand.equals(duplicateFindAttendanceCommand));

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
