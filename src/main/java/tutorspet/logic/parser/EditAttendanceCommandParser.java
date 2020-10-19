package tutorspet.logic.parser;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.EditAttendanceCommand.MESSAGE_NOT_EDITED;
import static tutorspet.logic.commands.EditAttendanceCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_WEEK;
import static tutorspet.logic.parser.ParserUtil.parseIndex;
import static tutorspet.logic.parser.ParserUtil.parseParticipationScore;
import static tutorspet.logic.parser.ParserUtil.parseWeek;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.EditAttendanceCommand;
import tutorspet.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;
import tutorspet.logic.parser.exceptions.ParseException;
import tutorspet.model.attendance.Week;

/**
 * Parses input arguments and create a new EditAttendanceCommand object.
 */
public class EditAttendanceCommandParser implements Parser<EditAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAttendanceCommand
     * and returns an EditAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX,
                PREFIX_STUDENT_INDEX, PREFIX_WEEK, PREFIX_PARTICIPATION_SCORE);

        Index moduleClassIndex;
        Index lessonIndex;
        Index studentIndex;
        Week week;

        boolean isModuleClassIndexPresent = argMultimap.getValue(PREFIX_CLASS_INDEX).isPresent();
        boolean isLessonIndexPresent = argMultimap.getValue(PREFIX_LESSON_INDEX).isPresent();
        boolean isStudentIndexPresent = argMultimap.getValue(PREFIX_STUDENT_INDEX).isPresent();
        boolean isWeekPresent = argMultimap.getValue(PREFIX_WEEK).isPresent();
        if (!isModuleClassIndexPresent || !isLessonIndexPresent || !isStudentIndexPresent || !isWeekPresent) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            lessonIndex = parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
            studentIndex = parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        week = parseWeek(argMultimap.getValue(PREFIX_WEEK).get());

        EditAttendanceDescriptor editAttendanceDescriptor = new EditAttendanceDescriptor();

        if (argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).isPresent()) {
            editAttendanceDescriptor.setParticipationScore(
                    parseParticipationScore(argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).get()));
        }

        if (!editAttendanceDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }

        return new EditAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, editAttendanceDescriptor);
    }
}
