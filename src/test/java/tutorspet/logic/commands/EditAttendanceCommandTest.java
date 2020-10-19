package tutorspet.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.ModuleClassUtil.manualReplaceLessonToModuleClass;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.TutorsPet;
import tutorspet.model.UserPrefs;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecord;
import tutorspet.model.attendance.AttendanceRecordList;
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

    private static final EditAttendanceCommand.EditAttendanceDescriptor EDITED_ATTENDANCE_DESCRIPTOR =
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
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        // manually edit attendance
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        ModuleClass editedModuleClass =
                manualEditAttendance(moduleClass, lesson, student, targetWeek, editedAttendance);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), targetWeek, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                studentIndex, targetWeek, new EditAttendanceCommand.EditAttendanceDescriptor());

        Student student = model.getFilteredStudentList().get(moduleClassIndex.getZeroBased());
        Attendance editedAttendance = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased())
                .getLessons().get(lessonIndex.getZeroBased())
                .getAttendanceRecordList()
                .getAttendance(student, targetWeek);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), targetWeek, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        showStudentAtIndex(model, INDEX_FIRST_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        // manually edit attendance
        ModuleClass moduleClassInFilteredList = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = moduleClassInFilteredList.getLessons().get(lessonIndex.getZeroBased());
        Student studentInFilteredList = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);

        ModuleClass editedModuleClass = manualEditAttendance(moduleClassInFilteredList, lesson,
                studentInFilteredList, targetWeek, editedAttendance);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
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

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                outOfBoundIndex, lessonIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;
        Week targetWeek = VALID_WEEK_1;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor = new EditAttendanceDescriptorBuilder()
                .withParticipationScore(INVALID_PARTICIPATION_SCORE_101).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, outOfBoundIndex, studentIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Week targetWeek = VALID_WEEK_1;

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, outOfBoundIndex, targetWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        EditAttendanceCommand.EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, studentIndex, invalidWeek, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_WEEK);
    }

    @Test
    public void execute_studentNotInClass_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                INDEX_THIRD_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
    }

    @Test
    public void execute_attendanceDoesNotExist_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(moduleClassIndex, lessonIndex,
                studentIndex, VALID_WEEK_5, DESC_ATTENDANCE_33);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_MISSING_STUDENT_ATTENDANCE);
    }

    @Test
    public void equals() {
        final EditAttendanceCommand standardCommand = new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, DESC_ATTENDANCE_33);

        // same values -> returns true
        EditAttendanceCommand.EditAttendanceDescriptor copyDescriptor =
                new EditAttendanceCommand.EditAttendanceDescriptor(DESC_ATTENDANCE_33);
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

    private ModuleClass manualEditAttendance(
            ModuleClass moduleClass, Lesson lesson, Student student, Week week, Attendance toEdit) {
        AttendanceRecordList attendanceRecordList = lesson.getAttendanceRecordList();
        AttendanceRecord attendanceRecord = attendanceRecordList.getAttendanceRecord(week);

        Map<UUID, Attendance> editedAttendanceRecordMap = new HashMap<>(attendanceRecord.getAttendanceRecord());
        editedAttendanceRecordMap.put(student.getUuid(), toEdit);
        AttendanceRecord updatedAttendanceRecord = new AttendanceRecord(editedAttendanceRecordMap);
        List<AttendanceRecord> updatedAttendanceRecordList =
                new ArrayList<>(attendanceRecordList.getAttendanceRecordList());
        updatedAttendanceRecordList.set(week.getZeroBasedWeekIndex(), updatedAttendanceRecord);
        Lesson editedLesson = new Lesson(lesson.getStartTime(), lesson.getEndTime(), lesson.getDay(),
                lesson.getNumberOfOccurrences(), lesson.getVenue(),
                new AttendanceRecordList(updatedAttendanceRecordList));
        ModuleClass editedModuleClass = manualReplaceLessonToModuleClass(moduleClass, lesson, editedLesson);

        return editedModuleClass;
    }
}
