package tutorspet.logic.commands.student;

import static java.util.Objects.requireNonNull;

import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.model.Model;

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
        model.commit(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
