package tutorspet.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.commons.core.Messages.MESSAGE_DUPLICATE_LESSON;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandFailure;
import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorspet.logic.commands.lesson.AddLessonCommand.MESSAGE_SUCCESS;
import static tutorspet.logic.util.ModuleClassUtil.addLessonToModuleClass;
import static tutorspet.testutil.Assert.assertThrows;
import static tutorspet.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static tutorspet.testutil.TypicalTutorsPet.getOnlyModuleClassTutorsPet;

import org.junit.jupiter.api.Test;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;
import tutorspet.testutil.LessonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddLessonCommand}.
 */
public class AddLessonCommandTest {

    private Model model = new ModelManager(getOnlyModuleClassTutorsPet(), new UserPrefs());

    @Test
    public void constructor_nullIndexes_throwNullPointerException() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        assertThrows(NullPointerException.class, () -> new AddLessonCommand(moduleClassIndex, null));
        Lesson lesson = new LessonBuilder().build();
        assertThrows(NullPointerException.class, () -> new AddLessonCommand(null, lesson));
    }

    @Test
    public void execute_unfilteredList_success() throws CommandException {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = new LessonBuilder().build();
        ModuleClass modifiedModuleClass = addLessonToModuleClass(moduleClass, lesson);

        String expectedMessage = String.format(MESSAGE_SUCCESS, lesson);
        Model expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
        expectedModel.setModuleClass(moduleClass, modifiedModuleClass);
        expectedModel.commit(expectedMessage);

        assertCommandSuccess(new AddLessonCommand(moduleClassIndex, lesson), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingLesson_failure() throws CommandException {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(moduleClassIndex.getZeroBased());
        Lesson lesson = new LessonBuilder().build();
        ModuleClass modifiedModuleClass = addLessonToModuleClass(moduleClass, lesson);

        // update model with modified class
        model.setModuleClass(moduleClass, modifiedModuleClass);

        AddLessonCommand addLessonCommand = new AddLessonCommand(moduleClassIndex, lesson);

        assertCommandFailure(addLessonCommand, model, MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void execute_invalidClassIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleClassList().size() + 1);
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, lesson);

        assertCommandFailure(addLessonCommand, model, MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Index moduleClassIndex = INDEX_FIRST_ITEM;

        Lesson lessonAtCom1 = new LessonBuilder().withVenue("COM1-0001").build();
        Lesson lessonAtCom2 = new LessonBuilder().withVenue("COM2-0002").build();
        AddLessonCommand addLessonCom1Command = new AddLessonCommand(moduleClassIndex, lessonAtCom1);
        AddLessonCommand addLessonCom2Command = new AddLessonCommand(moduleClassIndex, lessonAtCom2);

        // same object -> returns true
        assertTrue(addLessonCom1Command.equals(addLessonCom1Command));

        // same values -> returns true
        AddLessonCommand addLessonCom1CommandCopy = new AddLessonCommand(moduleClassIndex, lessonAtCom1);
        assertTrue(addLessonCom1Command.equals(addLessonCom1CommandCopy));

        // different type -> returns false
        assertFalse(addLessonCom1Command.equals(5));

        // null -> returns false
        assertFalse(addLessonCom1Command.equals(null));

        // different lessons -> returns false
        assertFalse(addLessonCom1Command.equals(addLessonCom2Command));
    }
}
