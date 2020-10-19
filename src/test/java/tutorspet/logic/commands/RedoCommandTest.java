package tutorspet.logic.commands;

import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.VersionedTutorsPetTest;
import tutorspet.testutil.TypicalTutorsPet;

public class RedoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_hasNextState_throwsCommandException() {
        model.commit(VersionedTutorsPetTest.COMMIT_MESSAGE_1);
        model.undo();

        String expectedMessage = String.format(String.format(RedoCommand.MESSAGE_SUCCESS,
                VersionedTutorsPetTest.COMMIT_MESSAGE_1));
        expectedModel.commit(VersionedTutorsPetTest.COMMIT_MESSAGE_1);

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noNextState_throwsCommandException() {
        model.commit(VersionedTutorsPetTest.COMMIT_MESSAGE_1);
        CommandTestUtil.assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NO_PREVIOUS_COMMAND);
    }
}
