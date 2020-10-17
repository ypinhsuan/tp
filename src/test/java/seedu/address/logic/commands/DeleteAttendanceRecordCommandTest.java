package seedu.address.logic.commands;

import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendance.Week;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAttendanceRecordCommand}
 */
public class DeleteAttendanceRecordCommandTest {

    private static final Week WEEK_ONE = new Week(Index.fromOneBased(1));
    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullModuleClassIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceRecordCommand(
                        null, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, WEEK_ONE));
    }

    @Test
    public void constructor_nullLessonIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceRecordCommand(
                INDEX_FIRST_ITEM, null, INDEX_FIRST_ITEM, WEEK_ONE));
    }

    @Test
    public void constructor_nullStudentIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceRecordCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null, WEEK_ONE));
    }

    @Test
    public void constructor_nullWeek_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteAttendanceRecordCommand(
                INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, INDEX_FIRST_ITEM, null));
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
