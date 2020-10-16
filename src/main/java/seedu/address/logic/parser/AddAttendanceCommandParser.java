package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Week;

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
                ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX,
                        PREFIX_WEEK, PREFIX_PARTICIPATION_SCORE);

        Index moduleClassIndex;
        Index lessonIndex;
        Index studentIndex;
        Week week;

        // parse relevant indexes
        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX, PREFIX_STUDENT_INDEX, PREFIX_WEEK)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            week = new Week(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_WEEK).get()));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE), pe);
        }

        // parse attendance data values
        boolean isParticipationScorePresent = argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).isPresent();
        if (!isParticipationScorePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAttendanceCommand.MESSAGE_USAGE));
        }

        int participationScore;

        try {
            participationScore = Integer.parseInt(argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).get().trim());
        } catch (NumberFormatException e) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }

        if (!Attendance.isValidParticipationScore(participationScore)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }

        Attendance attendance = new Attendance(participationScore);
        return new AddAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, attendance);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
