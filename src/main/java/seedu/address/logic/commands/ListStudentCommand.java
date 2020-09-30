package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.StudentInUuidCollectionPredicate;

/**
 * Lists all students (of a class, if specified) to the user.
 */
public class ListStudentCommand extends Command {

    public static final String COMMAND_WORD = "list-student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists students in a class or "
            + "all students if no class is specified.\n"
            + "Parameters: " + "[" + PREFIX_CLASS_INDEX + "CLASS_INDEX]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_CLASS_INDEX + "1";

    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all students.";
    public static final String MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS = "Listed all students in %1$s.";

    private final Optional<Index> moduleClassIndex;

    /**
     * Creates a ListStudentCommand to list all students.
     */
    public ListStudentCommand() {
        moduleClassIndex = Optional.empty();
    }

    /**
     * Creates a ListStudentCommand to list students in the class displayed at the specified index.
     */
    public ListStudentCommand(Index moduleClassIndex) {
        requireNonNull(moduleClassIndex);

        this.moduleClassIndex = Optional.of(moduleClassIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (moduleClassIndex.isPresent()) {
            return executeListInClass(model);
        } else {
            return executeListAll(model);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListStudentCommand // instanceof handles nulls
                && moduleClassIndex.equals(((ListStudentCommand) other).moduleClassIndex));
    }

    private CommandResult executeListAll(Model model) {
        assert model != null;

        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
    }

    private CommandResult executeListInClass(Model model) throws CommandException {
        assert model != null;
        assert moduleClassIndex.isPresent();

        List<ModuleClass> lastShownModuleClassList = model.getFilteredModuleClassList();

        if (moduleClassIndex.get().getZeroBased() >= lastShownModuleClassList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_CLASS_DISPLAYED_INDEX);
        }

        ModuleClass moduleClass = lastShownModuleClassList.get(moduleClassIndex.get().getZeroBased());
        Collection<UUID> studentUuids = moduleClass.getStudentUuids();
        model.updateFilteredStudentList(new StudentInUuidCollectionPredicate(studentUuids));
        return new CommandResult(String.format(MESSAGE_LIST_CLASS_SPECIFIC_SUCCESS, moduleClass));
    }
}
