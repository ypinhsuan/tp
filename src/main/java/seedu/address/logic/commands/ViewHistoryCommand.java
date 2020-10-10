package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.StateRecords;

/**
 * Lists all recorded {@code Command}s to the user.
 */
public class ViewHistoryCommand extends Command {

    public static final String COMMAND_WORD = "view-history";

    public static final String MESSAGE_TEMPLATE = "History:\n%s";
    public static final String MESSAGE_NO_COMMANDS = "There are no recorded commands";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StateRecords stateRecords = model.viewStateRecords();
        int currentStateIndex = stateRecords.getCurrentIndex();
        int stateCount = stateRecords.getStateRecords().size();

        if (stateCount == 0) {
            return new CommandResult(MESSAGE_NO_COMMANDS);
        }

        StringBuilder summary = new StringBuilder();

        for (int i = stateCount - 1; i >= 0; i--) {
            if (i == currentStateIndex) {
                summary.append("> " + stateRecords.getStateRecords().get(i) + "\n");
            } else {
                summary.append(stateRecords.getStateRecords().get(i) + "\n");
            }
        }

        return new CommandResult(String.format(MESSAGE_TEMPLATE, summary.toString()));
    }
}
