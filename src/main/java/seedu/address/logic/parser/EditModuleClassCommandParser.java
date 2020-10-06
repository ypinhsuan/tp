package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditModuleClassCommand;
import seedu.address.logic.commands.EditModuleClassCommand.EditModuleClassDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new EditModuleClassCommand object.
 */
public class EditModuleClassCommandParser implements Parser<EditModuleClassCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditModuleClassCommand
     * and returns an EditModuleClassCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditModuleClassCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleClassCommand.MESSAGE_USAGE), pe);
        }

        EditModuleClassDescriptor editModuleClassDescriptor = new EditModuleClassCommand.EditModuleClassDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editModuleClassDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (!editModuleClassDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditModuleClassCommand.MESSAGE_NOT_EDITED);
        }

        return new EditModuleClassCommand(index, editModuleClassDescriptor);
    }
}
