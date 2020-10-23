package tutorspet.logic.parser.lesson;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorspet.logic.commands.lesson.DisplayVenueCommand.MESSAGE_USAGE;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;
import static tutorspet.logic.parser.ParserUtil.arePrefixesPresent;
import static tutorspet.logic.parser.ParserUtil.parseIndex;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.lesson.DisplayVenueCommand;
import tutorspet.logic.parser.ArgumentMultimap;
import tutorspet.logic.parser.ArgumentTokenizer;
import tutorspet.logic.parser.Parser;
import tutorspet.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisplayVenueCommand object.
 */
public class DisplayVenueCommandParser implements Parser<DisplayVenueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DisplayVenueCommand and
     * returns a DisplayVenueCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DisplayVenueCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX);

        Index moduleClassIndex;
        Index lessonIndex;

        if (!arePrefixesPresent(argMultimap, PREFIX_CLASS_INDEX, PREFIX_LESSON_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        try {
            moduleClassIndex = parseIndex(argMultimap.getValue(PREFIX_CLASS_INDEX).get());
            lessonIndex = parseIndex(argMultimap.getValue(PREFIX_LESSON_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        return new DisplayVenueCommand(moduleClassIndex, lessonIndex);
    }
}
