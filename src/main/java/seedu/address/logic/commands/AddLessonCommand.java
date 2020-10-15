package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NO_OF_TIMES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;

/**
 * Adds a lesson to the lesson manager.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "add-lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the lesson manager. "
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_END_TIME + "END_TIME "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_NO_OF_TIMES + "NO_OF_TIMES\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASS_INDEX + "1 "
            + PREFIX_DAY + "TUESDAY "
            + PREFIX_START_TIME + "08:00 "
            + PREFIX_END_TIME + "10:00 "
            + PREFIX_VENUE + "COM1-0211 "
            + PREFIX_NO_OF_TIMES + "13";

    public static final String MESSAGE_SUCCESS = "New lesson added: %2$s %3$s %4$s %5$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists.";

    private final Lesson toAdd;

    /**
     * Creates an AddLessonCommand to add the specified {@code Lesson}.
     */
    public AddLessonCommand(Lesson lesson) {
        requireNonNull(lesson);

        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.addLesson(toAdd);
        String message = String.format(MESSAGE_SUCCESS, toAdd);
        model.commit(message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddLessonCommand // instanceof handles nulls
                && toAdd.equals(((AddLessonCommand) other).toAdd));
    }
}
