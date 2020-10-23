package tutorspet.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.CommandTestUtil.showModuleClassAtIndex;
import static tutorspet.logic.commands.lesson.DisplayVenueCommand.MESSAGE_SUCCESS;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalIndexes.INDEX_THIRD_ITEM;
import static tutorspet.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * Contains integration tests (interaction with the Model) for {@code DisplayVenueCommand}.
 */
public class DisplayVenueCommandTest {

    private final Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DisplayVenueCommand(null, INDEX_FIRST_ITEM));
        assertThrows(NullPointerException.class, () -> new DisplayVenueCommand(INDEX_FIRST_ITEM, null));
    }

    @Test
    public void execute_unfilteredList_success() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());

        assert lessonIndex.getZeroBased() < moduleClass.getLessons().size();

        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        assert moduleClass.hasLesson(lesson);

        String expectedMessage = String.format(MESSAGE_SUCCESS, moduleClass.getName().fullName, lesson.printLesson(),
                lesson.getVenue());
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        DisplayVenueCommand displayVenueCommand = new DisplayVenueCommand(moduleClassIndex, lessonIndex);

        assertCommandSuccess(displayVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleClassAtIndex(model, INDEX_FIRST_ITEM);

        Index moduleClassIndex = INDEX_FIRST_ITEM;
        Index lessonIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());

        assert lessonIndex.getZeroBased() < moduleClass.getLessons().size();

        Lesson lesson = moduleClass.getLessons().get(lessonIndex.getZeroBased());

        assert moduleClass.hasLesson(lesson);

        String expectedMessage = String.format(MESSAGE_SUCCESS, moduleClass.getName().fullName, lesson.printLesson(),
                lesson.getVenue());
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.updateFilteredModuleClassList(c -> c.isSameModuleClass(moduleClass));

        DisplayVenueCommand displayVenueCommand = new DisplayVenueCommand(moduleClassIndex, lessonIndex);

        assertCommandSuccess(displayVenueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClassIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Index lessonIndex = INDEX_FIRST_ITEM;

        DisplayVenueCommand displayVenueCommand = new DisplayVenueCommand(outOfBoundIndex, lessonIndex);

        assertCommandFailure(displayVenueCommand, model, MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLessonIndex_failure() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        assert moduleClassIndex.getZeroBased() < model.getFilteredModuleClassList().size();

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Index outOfBoundIndex = Index.fromOneBased(moduleClass.getLessons().size() + 1);

        DisplayVenueCommand displayVenueCommand = new DisplayVenueCommand(moduleClassIndex, outOfBoundIndex);

        assertCommandFailure(displayVenueCommand, model, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DisplayVenueCommand displayVenueCommand = new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);

        // same object -> returns true
        assertTrue(displayVenueCommand.equals(displayVenueCommand));

        // same value -> returns true
        DisplayVenueCommand duplicateDisplayVenueCommand = new DisplayVenueCommand(INDEX_FIRST_ITEM, INDEX_FIRST_ITEM);
        assertTrue(displayVenueCommand.equals(duplicateDisplayVenueCommand));

        // different type -> returns false
        assertFalse(displayVenueCommand.equals(5));

        // null -> returns false
        assertFalse(displayVenueCommand.equals(null));

        // different indexes -> returns false
        DisplayVenueCommand displayVenueCommandDifferentIndex = new DisplayVenueCommand(INDEX_THIRD_ITEM,
                INDEX_FIRST_ITEM);
        assertFalse(displayVenueCommand.equals(displayVenueCommandDifferentIndex));
    }
}
