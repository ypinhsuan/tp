package tutorspet.logic.parser.attendance;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.attendance.StatisticsCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.ParserUtil.arePrefixesPresent;
import static tutorspet.logic.parser.ParserUtil.parseIndex;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.attendance.StatisticsCommand;
import tutorspet.logic.parser.ArgumentMultimap;
import tutorspet.logic.parser.ArgumentTokenizer;
import tutorspet.logic.parser.Parser;
import tutorspet.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new StatisticsCommand object.
 */
public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatisticsCommand and
     * returns a StatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public StatisticsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX, PREFIX_STUDENT_INDEX);

        Index moduleClassIndex;
        Index studentIndex;

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_INDEX, PREFIX_STUDENT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            studentIndex = parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        return new StatisticsCommand(moduleClassIndex, studentIndex);
    }
}
