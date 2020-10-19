package tutorspet.logic.commands;

import static tutorspet.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import tutorspet.model.Model;
import tutorspet.model.ModelManager;
import tutorspet.model.UserPrefs;
import tutorspet.testutil.TypicalTutorsPet;

public class ClearStudentCommandTest {

    @Test
    public void execute_emptyTutorsPet_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commit(ClearStudentCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ClearStudentCommand(), model, ClearStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTutorsPet_success() {
        Model model = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalTutorsPet.getTypicalTutorsPet(), new UserPrefs());
        expectedModel.setTutorsPet(TypicalTutorsPet.getOnlyModuleClassTutorsPet());
        expectedModel.commit(ClearStudentCommand.MESSAGE_SUCCESS);

        assertCommandSuccess(new ClearStudentCommand(), model, ClearStudentCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
