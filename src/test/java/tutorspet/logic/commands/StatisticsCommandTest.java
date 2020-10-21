package tutorspet.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSONS_IN_MODULE_CLASS;
import static tutorspet.commons.core.Messages.MESSAGE_NO_LESSON_ATTENDED;
import static tutorspet.logic.commands.CommandTestUtil.VALID_NAME_CS2030_TUTORIAL;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static tutorspet.logic.commands.CommandTestUtil.showStudentAtIndex;
import static tutorspet.logic.commands.StatisticsCommand.MESSAGE_SUCCESS;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static tutorspet.testutil.TypicalLesson.LESSON_FRI_8_TO_10;
import static tutorspet.testutil.TypicalLesson.ONLINE_LESSON_TUE_1030_1130;
import static tutorspet.testutil.TypicalStudent.ALICE;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.List;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.model.student.Student;
import tutorspet.testutil.ModuleClassBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code StatisticsCommand}.
 */
public class StatisticsCommandTest {

    private final Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatisticsCommand(null, INDEX_FIRST_ITEM));
        assertThrows(NullPointerException.class, () -> new StatisticsCommand(INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_unfilteredList_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();
        assert studentIndex.getZeroBased() < model.getFilteredStudentList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        List<Lesson> listOfLessons = moduleClass.getLessons();

        assert !listOfLessons.isEmpty();

        String averageScore = "65";
        String lessonNotAttended = "Thursday 10:00 to 11:00: 3 4 5 6 7 8 9 10\n";

        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getName().fullName,
                moduleClass.getName().fullName, averageScore, lessonNotAttended);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, studentIndex);

        assertCommandSuccess(statisticsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);
        showStudentAtIndex(model, INDEX_FIRST_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();
        assert studentIndex.getZeroBased() < model.getFilteredStudentList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Student student = model.getFilteredStudentList().get(studentIndex.getZeroBased());
        List<Lesson> listOfLessons = moduleClass.getLessons();

        assert !listOfLessons.isEmpty();

        String averageScore = "65";
        String lessonNotAttended = "Thursday 10:00 to 11:00: 3 4 5 6 7 8 9 10\n";

        String expectedMessage = String.format(MESSAGE_SUCCESS, student.getName().fullName,
                moduleClass.getName().fullName, averageScore, lessonNotAttended);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(moduleClass));
        expectedModel.updateFilteredStudentList(s -> s.isSameStudent(student));

        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, studentIndex);

        assertCommandSuccess(statisticsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index studentIndex = INDEX_FIRST_ITEM;

        StatisticsCommand statisticsCommand = new StatisticsCommand(outOfBoundIndex, studentIndex);

        assertCommandFailure(statisticsCommand, model, MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);

        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, outOfBoundIndex);

        assertCommandFailure(statisticsCommand, model, MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
    }

    @Test
    public void execute_studentNotInClass_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index outOfBoundIndex = INDEX_THIRD_ITEM;

        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, outOfBoundIndex);

        assertCommandFailure(statisticsCommand, model, MESSAGE_INVALID_STUDENT_IN_MODULE_CLASS);
    }

    @Test
    public void execute_noLesson_failure() {
        Index moduleClassIndex = INDEX_THIRD_ITEM;
        Index studentIndex = INDEX_FIRST_ITEM;

        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, studentIndex);

        assertCommandFailure(statisticsCommand, model, MESSAGE_NO_LESSONS_IN_MODULE_CLASS);
    }

    @Test
    public void execute_noAttendance_failure() {
        ModuleClass moduleClass = new ModuleClassBuilder()
                .withName(VALID_NAME_CS2030_TUTORIAL)
                .withLessons(LESSON_FRI_8_TO_10, ONLINE_LESSON_TUE_1030_1130)
                .withStudentUuids(ALICE.getUuid())
                .build();
        model.addModuleClass(moduleClass);

        Index moduleClassIndex = Index.fromOneBased(model.getFilteredModuleClassList().size());
        Index studentIndex = INDEX_FIRST_ITEM;

        StatisticsCommand statisticsCommand = new StatisticsCommand(moduleClassIndex, studentIndex);

        assertCommandFailure(statisticsCommand, model, MESSAGE_NO_LESSON_ATTENDED);
    }

    @Test
    public void equals() {
        StatisticsCommand statisticsCommand = new StatisticsCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);

        // same object -> returns true
        assertTrue(statisticsCommand.equals(statisticsCommand));

        // same value -> returns true
        StatisticsCommand duplicateStatisticsCommand = new StatisticsCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        assertTrue(statisticsCommand.equals(duplicateStatisticsCommand));

        // different type -> returns false
        assertFalse(statisticsCommand.equals(5));

        // null -> returns false
        assertFalse(statisticsCommand.equals(null));

        // different indexes -> returns false
        StatisticsCommand statisticsCommandDifferentIndex = new StatisticsCommand(INDEX_THIRD_ITEM, INDEX_FIRST_ITEM);
        assertFalse(statisticsCommand.equals(statisticsCommandDifferentIndex));
    }
}
