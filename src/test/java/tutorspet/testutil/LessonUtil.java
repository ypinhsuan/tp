package tutorspet.testutil;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.AddLessonCommand;
import tutorspet.logic.commands.EditLessonCommand;
import tutorspet.logic.parser.CliSyntax;
import tutorspet.model.lesson.Lesson;

/**
 * A utility class for Lesson.
 */
public class LessonUtil {

    /**
     * Returns an add lesson command string for adding the {@code lesson}.
     */
    public static String getAddLessonCommand(Index moduleClassIndex, Lesson lesson) {
        return AddLessonCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_CLASS_INDEX + moduleClassIndex.getOneBased() + " "
                + getLessonDetails(lesson);
    }

    /**
     * Returns the part of the command string for the given {@code lesson}'s details.
     */
    public static String getLessonDetails(Lesson lesson) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_DAY + lesson.getDay().toString() + " ");
        sb.append(CliSyntax.PREFIX_START_TIME + lesson.getStartTime().toString() + " ");
        sb.append(CliSyntax.PREFIX_END_TIME + lesson.getEndTime().toString() + " ");
        sb.append(CliSyntax.PREFIX_VENUE + lesson.getVenue().toString() + " ");
        sb.append(CliSyntax.PREFIX_NUMBER_OF_OCCURRENCES + lesson.getNumberOfOccurrences().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditLessonDescriptor}'s details.
     */
    public static String getEditLessonDescriptorDetails(EditLessonCommand.EditLessonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getStartTime().ifPresent(startTime -> sb.append(CliSyntax.PREFIX_START_TIME)
                .append(startTime.toString()).append(" "));
        descriptor.getEndTime().ifPresent(endTime -> sb.append(CliSyntax.PREFIX_END_TIME)
                .append(endTime.toString()).append(" "));
        descriptor.getDay().ifPresent(day -> sb.append(CliSyntax.PREFIX_DAY).append(day.toString()).append(" "));
        descriptor.getVenue().ifPresent(venue -> sb.append(CliSyntax.PREFIX_VENUE)
                .append(venue.toString()).append(" "));

        return sb.toString();
    }
}
