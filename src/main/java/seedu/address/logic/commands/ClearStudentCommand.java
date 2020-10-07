package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears all students in the student manager.
 */
public class ClearStudentCommand extends Command {

    public static final String COMMAND_WORD = "clear-student";
    public static final String MESSAGE_SUCCESS = "All students in Tutor's Pet have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.deleteAllStudents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
