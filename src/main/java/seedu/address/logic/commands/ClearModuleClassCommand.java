package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears all classes in the student manager.
 */
public class ClearModuleClassCommand extends Command {

    public static final String COMMAND_WORD = "clear-class";
    public static final String MESSAGE_SUCCESS = "All classes in Tutor's Pet have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.deleteAllModuleClasses();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
