package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.ModuleClassUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAttendanceCommand}
 */
public class DeleteAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                null, lessonIndex, studentIndex, targetWeek));
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                moduleClassIndex, null, studentIndex, targetWeek));
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                moduleClassIndex, lessonIndex, null, targetWeek));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, null));
    }

    @Test
    public void execute_validIndexes_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        // delete week 1 attendance record
        Map<UUID, Attendance> record = lesson.getAttendanceRecordList()
                .getAttendanceRecord(targetWeek).getAttendanceRecord();
        Map<UUID, Attendance> updatedRecord = new HashMap<>(record);
        updatedRecord.remove(student.getUuid());
        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(updatedRecord);
        List<AttendanceRecord> updatedAttendanceRecords =
                new ArrayList<>(lesson.getAttendanceRecordList().getAttendanceRecordList());
        updatedAttendanceRecords.set(targetWeek.getZeroBasedWeekIndex(), updatedAttendanceRecord);

        Lesson modifiedLesson = new LessonBuilder(lesson)
                .withAttendanceRecordList(new AttendanceRecordList(updatedAttendanceRecords)).build();
        ModuleClass modifiedModuleClass =
                ModuleClassUtil.manualReplaceLessonToModuleClass(moduleClass, lesson, modifiedLesson);

        String expectedMessage = String.format(
                DeleteAttendanceCommand.MESSAGE_DELETE_ATTENDANCE_SUCCESS, targetWeek,
                student.getName(), modifiedLesson);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.commit(expectedMessage);

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandSuccess(deleteAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingAttendance_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_5;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        // delete non-existent week 5 attendance record
        Map<UUID, Attendance> record = lesson.getAttendanceRecordList()
                .getAttendanceRecord(targetWeek).getAttendanceRecord();
        Map<UUID, Attendance> updatedRecord = new HashMap<>(record);
        updatedRecord.remove(student.getUuid());
        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(updatedRecord);
        List<AttendanceRecord> updatedAttendanceRecords =
                new ArrayList<>(lesson.getAttendanceRecordList().getAttendanceRecordList());
        updatedAttendanceRecords.set(targetWeek.getZeroBasedWeekIndex(), updatedAttendanceRecord);

        Lesson modifiedLesson = new LessonBuilder(lesson)
                .withAttendanceRecordList(new AttendanceRecordList(updatedAttendanceRecords)).build();
        ModuleClass modifiedModuleClass =
                ModuleClassUtil.manualReplaceLessonToModuleClass(moduleClass, lesson, modifiedLesson);

        model.setModuleClass(moduleClass, modifiedModuleClass);

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandFailure(deleteAttendanceCommand, model, DeleteAttendanceCommand.MESSAGE_MISSING_ATTENDANCE);
    }

    @Test
    public void execute_invalidModuleClassIndex_failure() {
        Index moduleClassIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandFailure(deleteAttendanceCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index lessonIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandFailure(deleteAttendanceCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Week targetWeek = VALID_WEEK_1;

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandFailure(deleteAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidWeek_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        Week targetWeek =
                new Week(Index.fromOneBased(lesson.getAttendanceRecordList().getAttendanceRecordList().size() + 1));

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, targetWeek);

        assertCommandFailure(deleteAttendanceCommand, model, Messages.MESSAGE_INVALID_WEEK);
    }
}
