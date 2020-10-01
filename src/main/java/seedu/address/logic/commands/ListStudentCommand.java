package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all students to the user.
 */
public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "list-student";

    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all students.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ListStudentCommand);
    }
}
