package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

/**
 * Contains integration tests (interaction with the Model) for {@code AddAttendanceCommand}.
 */
public class AddAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullAttendance_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        VALID_WEEK, null));
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddAttendanceCommand(null, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        VALID_WEEK, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () ->
                new AddAttendanceCommand(INDEX_FIRST_ITEM, null, INDEX_FIRST_ITEM,
                        VALID_WEEK, VALID_ATTENDANCE));
        assertThrows(NullPointerException.class, () ->
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null,
                        VALID_WEEK, VALID_ATTENDANCE));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                        null, VALID_ATTENDANCE));
    }

    @Test
    public void execute_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        AttendanceRecord attendanceRecord = new AttendanceRecord(Map.of(student.getUuid(), VALID_ATTENDANCE));
        List<AttendanceRecord> attendanceRecords =
                new ArrayList<>(lesson.getAttendanceRecordList().getAttendanceRecordList());
        attendanceRecords.set(VALID_WEEK.getZeroBasedWeekIndex(), attendanceRecord);
        Lesson modifiedLesson = new LessonBuilder()
                .withStartTime(lesson.getStartTime())
                .withEndTime(lesson.getEndTime())
                .withDay(lesson.getDay())
                .withVenue(lesson.getVenue().venue)
                .withAttendanceRecordList(new AttendanceRecordList(attendanceRecords))
                .build();
        ModuleClass modifiedModuleClass = manualReplaceLessonToModuleClass(moduleClass, lesson, modifiedLesson);

        String expectedMessage =
                String.format(AddAttendanceCommand.MESSAGE_SUCCESS, student.getName(), VALID_WEEK, VALID_ATTENDANCE);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.commit(expectedMessage);

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, VALID_WEEK, VALID_ATTENDANCE);

        assertCommandSuccess(addAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingAttendance_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        AttendanceRecord attendanceRecord = new AttendanceRecord(Map.of(student.getUuid(), VALID_ATTENDANCE));
        List<AttendanceRecord> attendanceRecords =
                new ArrayList<>(lesson.getAttendanceRecordList().getAttendanceRecordList());
        attendanceRecords.set(VALID_WEEK.getZeroBasedWeekIndex(), attendanceRecord);
        Lesson modifiedLesson = new LessonBuilder()
                .withStartTime(lesson.getStartTime())
                .withEndTime(lesson.getEndTime())
                .withDay(lesson.getDay())
                .withVenue(lesson.getVenue().venue)
                .withAttendanceRecordList(new AttendanceRecordList(attendanceRecords))
                .build();
        ModuleClass modifiedModuleClass = manualReplaceLessonToModuleClass(moduleClass, lesson, modifiedLesson);

        model.setModuleClass(moduleClass, modifiedModuleClass);

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, VALID_WEEK, VALID_ATTENDANCE);

        assertCommandFailure(addAttendanceCommand, model, AddAttendanceCommand.MESSAGE_DUPLICATE_ATTENDANCE);
    }

    @Test
    public void execute_invalidClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(outOfBoundIndex, lessonIndex, studentIndex, VALID_WEEK, VALID_ATTENDANCE);

        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(moduleClassIndex, outOfBoundIndex, studentIndex, VALID_WEEK, VALID_ATTENDANCE);

        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(moduleClassIndex, lessonIndex, outOfBoundIndex, VALID_WEEK, VALID_ATTENDANCE);

        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        AddAttendanceCommand addAttendanceCommand =
                new AddAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, invalidWeek, VALID_ATTENDANCE);

        assertCommandFailure(addAttendanceCommand, model, Messages.MESSAGE_INVALID_WEEK);
    }

    @Test
    public void equals() {
        Attendance attendanceScore90 = new Attendance(90);
        Attendance attendanceScore80 = new Attendance(80);
        Week week1 = new Week(Index.fromOneBased(1));
        Week week2 = new Week(Index.fromOneBased(2));
        AddAttendanceCommand addAttendanceCommand = new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, week1, attendanceScore90);

        // same object -> returns true
        assertTrue(addAttendanceCommand.equals(addAttendanceCommand));

        // same value -> returns true
        AddAttendanceCommand duplicateAddAttendanceCommand = new AddAttendanceCommand(INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, week1, attendanceScore90);
        assertTrue(addAttendanceCommand.equals(duplicateAddAttendanceCommand));

        // different type -> returns false
        assertFalse(addAttendanceCommand.equals(5));

        // null -> returns false
        assertFalse(addAttendanceCommand.equals(null));

        // different attendances -> returns false
        AddAttendanceCommand addAttendanceCommandDifferentAttendance =
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, week1,
                        attendanceScore80);
        assertFalse(addAttendanceCommand.equals(addAttendanceCommandDifferentAttendance));

        // different weeks -> return false
        AddAttendanceCommand addAttendanceCommandDifferentWeek =
                new AddAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, week2,
                        attendanceScore90);
        assertFalse(addAttendanceCommand.equals(addAttendanceCommandDifferentWeek));
    }

    /**
     * Returns a new {@code ModuleClass} based on the given {@code moduleClass} but with the specified
     * {@code targetLesson} replaced.
     */
    private static ModuleClass manualReplaceLessonToModuleClass(ModuleClass moduleClass,
                                                                Lesson targetLesson, Lesson modifiedLesson) {
        List<Lesson> lessons = new ArrayList<>(moduleClass.getLessons());

        for (Lesson lesson : lessons) {
            if (lesson.isSameLesson(targetLesson)) {
                lessons.set(lessons.indexOf(lesson), modifiedLesson);
            }
        }

        return new ModuleClass(moduleClass.getName(), moduleClass.getStudentUuids(), lessons);
    }
}
