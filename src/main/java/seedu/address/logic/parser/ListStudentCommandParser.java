package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListStudentCommandParser object.
 */
public class ListStudentCommandParser implements Parser<ListStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListStudentCommand
     * and returns a ListStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ListStudentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX);

        if (argMultimap.getValue(PREFIX_CLASS_INDEX).isEmpty()) {
            return new ListStudentCommand();
        }

        try {
            Index moduleClassIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            return new ListStudentCommand(moduleClassIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListStudentCommand.MESSAGE_USAGE), pe);
        }
    }
}
