package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.VersionedTutorsPetTest.COMMIT_MESSAGE_1;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_hasNextState_throwsCommandException() {
        model.commit(COMMIT_MESSAGE_1);
        model.undo();

        String expectedMessage = String.format(String.format(RedoCommand.MESSAGE_SUCCESS, COMMIT_MESSAGE_1));
        expectedModel.commit(COMMIT_MESSAGE_1);

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noNextState_throwsCommandException() {
        model.commit(COMMIT_MESSAGE_1);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_PREVIOUS_COMMAND);
    }
}
