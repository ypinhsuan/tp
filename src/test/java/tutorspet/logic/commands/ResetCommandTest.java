package tutorspet.logic.commands;

import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.TutorsPet;
import tutorspet.model.UserPrefs;
import tutorspet.testutil.TypicalTutorsPet;

public class ResetCommandTest {

    @Test
    public void execute_emptyTutorsPet_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commit(ResetCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ResetCommand(), model, ResetCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTutorsPet_success() {
        Model model = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        expectedModel.setTutorsPet(new TutorsPet());
        expectedModel.commit(ResetCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ResetCommand(), model, ResetCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
