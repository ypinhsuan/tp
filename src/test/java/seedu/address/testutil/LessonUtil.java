package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_OF_OCCURRENCES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.model.lesson.Lesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add lesson command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Lesson lesson) {
        return AddLessonCommand.COMMAND_WORD + " "
                + PREFIX_CLASS_INDEX + INDEX_FIRST_ITEM.getOneBased() + " "
                + getLessonDetails(lesson);
    }

    /**
     * Returns the part of the command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DAY + lesson.getDay().toString() + " ");
        sb.append(PREFIX_START_TIME + lesson.getStartTime().toString() + " ");
        sb.append(PREFIX_END_TIME + lesson.getEndTime().toString() + " ");
        sb.append(PREFIX_VENUE + lesson.getVenue().toString() + " ");
        sb.append(PREFIX_NUMBER_OF_OCCURRENCES + lesson.getNumberOfOccurrences().toString() + " ");
        return sb.toString();
    }
}
