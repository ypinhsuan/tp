package tutorspet.logic.commands.moduleclass;

import static java.util.Objects.requireNonNull;
import static tutorspet.logic.parser.CliSyntax.PREFIX_NAME;

import tutorspet.commons.core.Messages;
import tutorspet.logic.commands.Command;
import tutorspet.logic.commands.CommandResult;
import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.Model;
import tutorspet.model.moduleclass.ModuleClass;

/**
 * Adds a class to the student manager.
 */
public class AddModuleClassCommand extends Command {

    public static final String COMMAND_WORD = "add-class";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a class to the student manager. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103T Tutorial";

    public static final String MESSAGE_SUCCESS = "New class added:\n%1$s.";

    private final ModuleClass toAdd;

    /**
     * Creates an AddModuleClassCommand to add the specified {@code ModuleClass}.
     */
    public AddModuleClassCommand(ModuleClass moduleClass) {
        requireNonNull(moduleClass);

        toAdd = moduleClass;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModuleClass(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_MODULE_CLASS);
        }

        model.addModuleClass(toAdd);
        String message = String.format(MESSAGE_SUCCESS, toAdd);
        model.commit(message);
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleClassCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleClassCommand) other).toAdd));
    }
}
