package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ATTENDANCE_33;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ATTENDANCE_80;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_SCORE_33;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_5;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TutorsPet;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.attendance.AttendanceRecordList;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditAttendanceDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EditAttendanceCommand}.
 */
public class EditAttendanceCommandTest {

    private static final EditAttendanceDescriptor EDITED_ATTENDANCE_DESCRIPTOR =
            new EditAttendanceDescriptorBuilder(new Attendance(10)).build();

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullModuleClassIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                null, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullLessonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                INDEX_FIRST_ITEM, null, INDEX_FIRST_ITEM, VALID_WEEK_1, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null, VALID_WEEK_1, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null, EDITED_ATTENDANCE_DESCRIPTOR));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1, editAttendanceDescriptor);

        // manually edit attendance
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Lesson lesson = moduleClass.getLessons().get(INDEX_FIRST_ITEM.getZeroBased());
        Student student = model.getFilteredStudentList().get(INDEX_FIRST_ITEM.getZeroBased());
        Week week = VALID_WEEK_1;
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);
        ModuleClass editedModuleClass = manualEditAttendance(moduleClass, lesson, student, week, editedAttendance);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), VALID_WEEK_1, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM,
                INDEX_FIRST_ITEM, VALID_WEEK_1, new EditAttendanceDescriptor());

        Student student = model.getFilteredStudentList().get(INDEX_FIRST_ITEM.getZeroBased());
        Attendance editedAttendance = model.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased())
                .getLessons().get(INDEX_FIRST_ITEM.getZeroBased())
                .getAttendanceRecordList()
                .getAttendance(student, VALID_WEEK_1);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                student.getName(), VALID_WEEK_1, editedAttendance);
        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        showStudentAtIndex(model, INDEX_FIRST_ITEM);

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1, editAttendanceDescriptor);

        // manually edit attendance
        ModuleClass moduleClassInFilteredList = model.getFilteredModuleClassList().get(INDEX_FIRST_ITEM.getZeroBased());
        Lesson lesson = moduleClassInFilteredList.getLessons().get(INDEX_FIRST_ITEM.getZeroBased());
        Student studentInFilteredList = model.getFilteredStudentList().get(INDEX_FIRST_ITEM.getZeroBased());
        Week week = VALID_WEEK_1;
        Attendance editedAttendance = new Attendance(VALID_PARTICIPATION_SCORE_33);

        ModuleClass editedModuleClass =
                manualEditAttendance(moduleClassInFilteredList, lesson, studentInFilteredList, week, editedAttendance);

        String expectedMessage = String.format(EditAttendanceCommand.MESSAGE_EDIT_ATTENDANCE_SUCCESS,
                studentInFilteredList.getName(), VALID_WEEK_1, editedAttendance);

        Model expectedModel = new ModelManager(new TutorsPet(model.getTutorsPet()), new UserPrefs());
        expectedModel.updateFilteredStudentList(s -> s.equals(studentInFilteredList));
        expectedModel.setModuleClass(moduleClassInFilteredList, editedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(editAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                outOfBoundIndex, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, VALID_WEEK_1, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, outOfBoundIndex, studentIndex, VALID_WEEK_1, editAttendanceDescriptor);

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        EditAttendanceDescriptor editAttendanceDescriptor =
                new EditAttendanceDescriptorBuilder(DESC_ATTENDANCE_33).build();
        EditAttendanceCommand editAttendanceCommand = new EditAttendanceCommand(
                moduleClassIndex, lessonIndex, outOfBoundIndex, VALID_WEEK_1, editAttendanceDescriptor);

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

        EditAttendanceDescriptor editAttendanceDescriptor =
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

        assertCommandFailure(editAttendanceCommand, model, Messages.MESSAGE_INVALID_STUDENT_IN_ATTENDANCE_RECORD);
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
        List<Lesson> editedLessonList = Arrays.asList(editedLesson);
        ModuleClass editedModuleClass =
                new ModuleClass(moduleClass.getName(), moduleClass.getStudentUuids(), editedLessonList);

        return editedModuleClass;
    }
}
