package tutorspet.logic.parser;

import static java.util.Objects.requireNonNull;

import tutorspet.commons.core.Messages;
import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.DeleteLessonCommand;
import tutorspet.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLessonCommand object.
 */
public class DeleteLessonCommandParser implements Parser<DeleteLessonCommand> {

    /**
     * Parses the given {@code String} of argument in the context of the DeleteLessonCommand
     * and returns a DeleteLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CLASS_INDEX, CliSyntax.PREFIX_LESSON_INDEX);

        Index moduleClassIndex;
        Index lessonIndex;

        boolean isModuleClassIndexPresent = argMultimap.getValue(CliSyntax.PREFIX_CLASS_INDEX).isPresent();
        boolean isLessonIndexPresent = argMultimap.getValue(CliSyntax.PREFIX_LESSON_INDEX).isPresent();
        if (!isModuleClassIndexPresent || !isLessonIndexPresent) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_CLASS_INDEX).get());
            lessonIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_LESSON_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteLessonCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteLessonCommand(moduleClassIndex, lessonIndex);
    }
}
