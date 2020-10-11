package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.StateRecords;

/**
 * Lists all recorded {@code Command}s to the user.
 */
public class ViewHistoryCommand extends Command {

    public static final String COMMAND_WORD = "view-history";

    public static final String CURRENT_INDICATOR = "> ";

    public static final String MESSAGE_TEMPLATE = "History:%s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StateRecords stateRecords = model.viewStateRecords();
        int currentStateIndex = stateRecords.getCurrentIndex();
        int stateCount = stateRecords.getStateRecords().size();

        assert stateCount != 0;

        StringBuilder summary = new StringBuilder();

        for (int i = stateCount - 1; i >= 0; i--) {
            if (i == currentStateIndex) {
                summary.append("\n" + CURRENT_INDICATOR + stateRecords.getStateRecords().get(i));
            } else {
                summary.append("\n" + stateRecords.getStateRecords().get(i));
            }
        }

        return new CommandResult(String.format(MESSAGE_TEMPLATE, summary.toString()));
    }
}
