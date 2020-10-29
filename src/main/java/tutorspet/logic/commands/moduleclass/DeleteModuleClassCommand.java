package tutorspet.logic.commands.moduleclass;

import static java.util.Objects.requireNonNull;
import static tutorspet.commons.core.Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX;

import java.util.List;

import tutorspet.commons.core.index.Index;
import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * Deletes a class identified using it's displayed index from the application.
 */
public class DeleteModuleClassCommand extends Command {

    public static final String COMMAND_WORD = "delete-class";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the class identified by the index number used in the displayed class list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted class:\n%1$s.";
    public static final String MESSAGE_COMMIT = "Deleted class: %1$s.";

    private final Index targetIndex;

    public DeleteModuleClassCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<ModuleClass> lastShownList = model.getFilteredModuleClassList();

        if (targetIndex.getOneBased() > lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        ModuleClass moduleClassToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteModuleClass(moduleClassToDelete);
        model.commit(String.format(MESSAGE_COMMIT, moduleClassToDelete.getName()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleClassToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleClassCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteModuleClassCommand) other).targetIndex)); // state check
    }
}
