package tutorspet.logic.parser;

import static java.util.Objects.requireNonNull;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.AddAttendanceCommand;
import tutorspet.logic.parser.exceptions.ParseException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.Week;

/**
 * Parses input arguments and creates a new AddAttendanceCommand object.
 */
public class AddAttendanceCommandParser implements Parser<AddAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAttendanceCommand
     * and returns an AddAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CLASS_INDEX, CliSyntax.PREFIX_LESSON_INDEX,
                        CliSyntax.PREFIX_STUDENT_INDEX, CliSyntax.PREFIX_WEEK, CliSyntax.PREFIX_PARTICIPATION_SCORE);

        Index moduleClassIndex;
        Index lessonIndex;
        Index studentIndex;

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CLASS_INDEX, CliSyntax.PREFIX_LESSON_INDEX,
                CliSyntax.PREFIX_STUDENT_INDEX, CliSyntax.PREFIX_WEEK, CliSyntax.PREFIX_PARTICIPATION_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAttendanceCommand.MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_CLASS_INDEX).get());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_LESSON_INDEX).get());
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_STUDENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE), pe);
        }

        Week week = ParserUtil.parseWeek(argMultimap.getValue(CliSyntax.PREFIX_WEEK).get());
        int participationScore =
                ParserUtil.parseParticipationScore(argMultimap.getValue(CliSyntax.PREFIX_PARTICIPATION_SCORE).get());

        Attendance attendance = new Attendance(participationScore);
        return new AddAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, attendance);
    }
}
