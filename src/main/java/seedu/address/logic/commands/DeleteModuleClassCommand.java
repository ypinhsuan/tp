package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.moduleclass.ModuleClass;

public class DeleteModuleClassCommand extends Command {

    public static final String COMMAND_WORD = "delete-class";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the class identified by the index number used in the displayed class list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MODULE_CLASS_SUCCESS = "Delete Class: %1$s";

    private final Index targetIndex;

    public DeleteModuleClassCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<ModuleClass> lastShownList = model.getFilteredModuleClassList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        ModuleClass moduleClassToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteModuleClass(moduleClassToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_CLASS_SUCCESS, moduleClassToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleClassCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteModuleClassCommand) other).targetIndex)); // state check
    }
}
