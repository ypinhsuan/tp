package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

public class EditLessonCommandTest {

    private Model model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

}
