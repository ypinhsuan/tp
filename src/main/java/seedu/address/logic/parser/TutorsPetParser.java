package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddModuleClassCommand;
import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.commands.ClearModuleClassCommand;
import seedu.address.logic.commands.ClearStudentCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteModuleClassCommand;
import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.commands.EditModuleClassCommand;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindModuleClassCommand;
import seedu.address.logic.commands.FindStudentCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListModuleClassCommand;
import seedu.address.logic.commands.ListStudentCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResetCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class TutorsPetParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string.
     * @return the command based on the user input.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddStudentCommand.COMMAND_WORD:
            return new AddStudentCommandParser().parse(arguments);

        case ListStudentCommand.COMMAND_WORD:
            return new ListStudentCommandParser().parse(arguments);

        case EditStudentCommand.COMMAND_WORD:
            return new EditStudentCommandParser().parse(arguments);

        case DeleteStudentCommand.COMMAND_WORD:
            return new DeleteStudentCommandParser().parse(arguments);

        case ClearStudentCommand.COMMAND_WORD:
            return new ClearStudentCommand();

        case FindStudentCommand.COMMAND_WORD:
            return new FindStudentCommandParser().parse(arguments);

        case AddModuleClassCommand.COMMAND_WORD:
            return new AddModuleClassCommandParser().parse(arguments);

        case ListModuleClassCommand.COMMAND_WORD:
            return new ListModuleClassCommand();

        case EditModuleClassCommand.COMMAND_WORD:
            return new EditModuleClassCommandParser().parse(arguments);

        case DeleteModuleClassCommand.COMMAND_WORD:
            return new DeleteModuleClassCommandParser().parse(arguments);

        case ClearModuleClassCommand.COMMAND_WORD:
            return new ClearModuleClassCommand();

        case FindModuleClassCommand.COMMAND_WORD:
            return new FindModuleClassCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case LinkCommand.COMMAND_WORD:
            return new LinkCommandParser().parse(arguments);

        case UnlinkCommand.COMMAND_WORD:
            return new UnlinkCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
