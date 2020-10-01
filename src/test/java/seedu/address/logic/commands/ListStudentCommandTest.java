package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListStudentCommand.
 */
public class ListStudentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(model.getTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_noIndexStudentListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListStudentCommand(), model,
                ListStudentCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noIndexStudentListIsFiltered_showsEverything() {
        showStudentAtIndex(model, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ListStudentCommand(), model,
                ListStudentCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListStudentCommand listStudentCommand = new ListStudentCommand();

        // ListStudentCommand -> returns true
        ListStudentCommand listStudentCommandCopy = new ListStudentCommand();
        assertTrue(listStudentCommand.equals(listStudentCommandCopy));

        // same object -> returns true
        assertTrue(listStudentCommand.equals(listStudentCommand));

        // null -> returns false
        assertFalse(listStudentCommand.equals(null));

        // different type -> returns false
        assertFalse(listStudentCommand.equals(5));
    }
}
