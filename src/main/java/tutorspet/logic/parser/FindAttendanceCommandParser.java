package tutorspet.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.FindAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.parser.ParserUtil.arePrefixesPresent;
import static tutorspet.logic.parser.ParserUtil.parseIndex;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.FindAttendanceCommand;
import tutorspet.logic.parser.exceptions.ParseException;
import tutorspet.model.attendance.Week;

/**
 * Parses input arguments and creates a new FindAttendanceCommand object.
 */
public class FindAttendanceCommandParser implements Parser<FindAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAttendanceCommand
     * and returns a FindAttendanceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public FindAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX,
                        PREFIX_WEEK);

        Index moduleClassIndex;
        Index lessonIndex;
        Index studentIndex;

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX,
                PREFIX_WEEK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            lessonIndex = parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
            studentIndex = parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        Week week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        return new FindAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week);
    }
}
