package tutorspet.logic.commands;

import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.model.VersionedTutorsPetTest;
import tutorspet.testutil.TypicalTutorsPet;

public class UndoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
    }

    @Test
    public void execute_hasPreviousState_success() {
        model.commit(VersionedTutorsPetTest.COMMIT_MESSAGE_1);

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, VersionedTutorsPetTest.COMMIT_MESSAGE_1);
        expectedModel.commit(VersionedTutorsPetTest.COMMIT_MESSAGE_1);
        expectedModel.undo();

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noPreviousState_throwsCommandException() {
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NO_PREVIOUS_COMMAND);
    }
}
