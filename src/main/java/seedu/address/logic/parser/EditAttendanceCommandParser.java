package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPATION_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAttendanceCommand;
import seedu.address.logic.commands.EditAttendanceCommand.EditAttendanceDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Week;

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
        boolean isStudentPresent = argMultimap.getValue(PREFIX_STUDENT_INDEX).isPresent();
        boolean isWeekPresent = argMultimap.getValue(PREFIX_WEEK).isPresent();

        if (!isModuleClassIndexPresent || !isLessonIndexPresent || !isStudentPresent || !isWeekPresent) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAttendanceCommand.MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAttendanceCommand.MESSAGE_USAGE), pe);
        }

        week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());

        EditAttendanceDescriptor editAttendanceDescriptor = new EditAttendanceDescriptor();

        if (argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).isPresent()) {
            editAttendanceDescriptor.setParticipationScore(
                    ParserUtil.parseParticipationScore(argMultimap.getValue(PREFIX_PARTICIPATION_SCORE).get()));
        }

        if (!editAttendanceDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAttendanceCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAttendanceCommand(moduleClassIndex, lessonIndex, studentIndex, week, editAttendanceDescriptor);
    }
}
