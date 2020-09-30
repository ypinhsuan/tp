package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindModuleClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.components.name.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindModuleClassCommand object.
 */
public class FindModuleClassCommandParser implements Parser<FindModuleClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindModuleClassCommand
     * and returns a FindModuleClassCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindModuleClassCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindModuleClassCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindModuleClassCommand(new NameContainsKeywordsPredicate<>(Arrays.asList(nameKeywords)));
    }
}
