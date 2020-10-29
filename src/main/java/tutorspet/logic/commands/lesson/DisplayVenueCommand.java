package tutorspet.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;
import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static tutorspet.logic.parser.CliSyntax.PREFIX_LESSON_INDEX;

import java.util.List;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * Displays the venue for a specified lesson in a specified class in the student manager.
 */
public class DisplayVenueCommand extends Command {

    public static final String COMMAND_WORD = "display-venue";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the venue for a lesson.\n"
            + "Note: All indexes must be positive integers.\n"
            + "Parameters: "
            + PREFIX_CLASS_INDEX + "CLASS_INDEX "
            + PREFIX_LESSON_INDEX + "LESSON_INDEX";

    public static final String MESSAGE_SUCCESS = "Venue for %1$s %2$s:\n%3$s";

    private final Index moduleClassIndex;
    private final Index lessonIndex;

    /**
     * Creates a DisplayVenueCommand to display the venue for a specified lesson in a specified class
     * in the student manager.
     */
    public DisplayVenueCommand(Index moduleClassIndex, Index lessonIndex) {
        requireAllNonNull(moduleClassIndex, lessonIndex);

        this.moduleClassIndex = moduleClassIndex;
        this.lessonIndex = lessonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<ModuleClass> lastShownModuleClassList = model.getFilteredModuleClassList();

        if (moduleClassIndex.getOneBased() > lastShownModuleClassList.size()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        ModuleClass targetModuleClass = lastShownModuleClassList.get(moduleClassIndex.getZeroBased());

        if (lessonIndex.getOneBased() > targetModuleClass.getLessons().size()) {
            throw new CommandException(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson targetLesson = targetModuleClass.getLessons().get(lessonIndex.getZeroBased());
        String message = String.format(MESSAGE_SUCCESS, targetModuleClass.getName().fullName,
                targetLesson.printLesson(), targetLesson.getVenue());
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayVenueCommand // instanceof handles nulls
                && moduleClassIndex.equals(((DisplayVenueCommand) other).moduleClassIndex)
                && lessonIndex.equals(((DisplayVenueCommand) other).lessonIndex));
    }
}
