package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULE_CLASS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes the most recent undone {@code Command}.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Successfully redone: %s";

    public static final String MESSAGE_NO_PREVIOUS_COMMAND = "There are no commands to redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_NO_PREVIOUS_COMMAND);
        }

        String commandMessage = model.redo();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.updateFilteredModuleClassList(PREDICATE_SHOW_ALL_MODULE_CLASS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, commandMessage));
    }
}
