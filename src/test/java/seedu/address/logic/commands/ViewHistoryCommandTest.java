package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.VersionedTutorsPet.INITIAL_COMMIT_MESSAGE;
import static seedu.address.model.VersionedTutorsPetTest.COMMIT_MESSAGE_1;
import static seedu.address.model.VersionedTutorsPetTest.COMMIT_MESSAGE_2;
import static seedu.address.model.VersionedTutorsPetTest.COMMIT_MESSAGE_3;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewHistoryCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        model.commit(COMMIT_MESSAGE_1);
        model.commit(COMMIT_MESSAGE_2);
        model.commit(COMMIT_MESSAGE_3);
        expectedModel = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel.commit(COMMIT_MESSAGE_1);
        expectedModel.commit(COMMIT_MESSAGE_2);
        expectedModel.commit(COMMIT_MESSAGE_3);
    }

    @Test
    public void execute_latestState_success() {
        String expectedMessage = String.format(ViewHistoryCommand.MESSAGE_TEMPLATE,
                "\n" + ViewHistoryCommand.CURRENT_INDICATOR + COMMIT_MESSAGE_3 + "\n"
                + COMMIT_MESSAGE_2 + "\n"
                + COMMIT_MESSAGE_1 + "\n"
                + INITIAL_COMMIT_MESSAGE);
        assertCommandSuccess(new ViewHistoryCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_earliestState_success() {
        model.undo();
        model.undo();
        model.undo();
        expectedModel.undo();
        expectedModel.undo();
        expectedModel.undo();

        String expectedMessage = String.format(ViewHistoryCommand.MESSAGE_TEMPLATE,
                "\n" + COMMIT_MESSAGE_3 + "\n"
                + COMMIT_MESSAGE_2 + "\n"
                + COMMIT_MESSAGE_1 + "\n"
                + ViewHistoryCommand.CURRENT_INDICATOR + INITIAL_COMMIT_MESSAGE);

        assertCommandSuccess(new ViewHistoryCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_intermediateState_success() {
        model.undo();
        expectedModel.undo();

        String expectedMessage = String.format(ViewHistoryCommand.MESSAGE_TEMPLATE,
                "\n" + COMMIT_MESSAGE_3 + "\n"
                + ViewHistoryCommand.CURRENT_INDICATOR + COMMIT_MESSAGE_2 + "\n"
                + COMMIT_MESSAGE_1 + "\n"
                + INITIAL_COMMIT_MESSAGE);

        assertCommandSuccess(new ViewHistoryCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noPreviousState_success() {
        model = new ModelManager(getTypicalTutorsPet(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalTutorsPet(), new UserPrefs());

        String expectedMessage = String.format(ViewHistoryCommand.MESSAGE_TEMPLATE,
                "\n" + ViewHistoryCommand.CURRENT_INDICATOR + INITIAL_COMMIT_MESSAGE);

        assertCommandSuccess(new ViewHistoryCommand(), model, expectedMessage, expectedModel);
    }
}
