package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;
import seedu.address.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAttendanceCommand}
 */
public class DeleteAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullModuleClassIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                        null, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK));
    }

    @Test
    public void constructor_nullLessonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                INDEX_FIRST_ITEM, null, INDEX_FIRST_ITEM, VALID_WEEK));
    }

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null, VALID_WEEK));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_validIndexes_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        AttendanceRecord attendanceRecord = new AttendanceRecord(Map.of(student.getUuid(), VALID_ATTENDANCE));
        List<AttendanceRecord> attendanceRecords =
                new ArrayList<>(lesson.getAttendanceRecordList().getAttendanceRecordList());
        attendanceRecords.remove(attendanceRecord);
        Lesson modifiedLesson = new LessonBuilder()
                .withStartTime(lesson.getStartTime())
                .withEndTime(lesson.getEndTime())
                .withDay(lesson.getDay())
                .withVenue(lesson.getVenue().venue)
                .withAttendanceRecordList(new AttendanceRecordList(attendanceRecords))
                .build();
        ModuleClass modifiedModuleClass = manualReplaceLessonToModuleClass(moduleClass, lesson, modifiedLesson);

        String expectedMessage = String.format(
                DeleteAttendanceCommand.MESSAGE_DELETE_ATTENDANCE_SUCCESS, VALID_WEEK,
                student.getName(), modifiedLesson);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.commit(expectedMessage);

        DeleteAttendanceCommand deleteAttendanceCommand =
                new DeleteAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, VALID_WEEK);

        assertCommandSuccess(deleteAttendanceCommand, model, expectedMessage, expectedModel);
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
