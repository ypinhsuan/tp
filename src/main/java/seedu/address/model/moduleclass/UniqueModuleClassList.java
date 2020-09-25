package seedu.address.model.moduleclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.moduleclass.exceptions.DuplicateModuleClassException;
import seedu.address.model.moduleclass.exceptions.ModuleClassNotFoundException;

/**
 * A list of ModuleClasses that enforces uniqueness between its elements and does not allow nulls.
 * A ModuleClass is considered unique by comparing using {@code ModuleClass#isSameModuleClass(ModuleClass)}.
 * As such, adding and updating of ModuleClass uses ModuleClass#isSameModuleClass(ModuleClass) for equality
 * so as to ensure that the ModuleClass being added or updated is unique in terms of identity in the
 * UniqueModuleClassList. However, the removal of a ModuleClass uses ModuleClass#equals(Object) so as to ensure
 * that the ModuleClass with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ModuleClass#isSameModuleClass(ModuleClass)
 */
public class UniqueModuleClassList implements Iterable<ModuleClass> {

    private final ObservableList<ModuleClass> internalList = FXCollections.observableArrayList();
    private final ObservableList<ModuleClass> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent ModuleClass as the given argument.
     */
    public boolean contains(ModuleClass toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameModuleClass);
    }

    /**
     * Adds a ModuleClass to the list.
     * The ModuleClass must not already exist in the list.
     */
    public void add(ModuleClass toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleClassException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ModuleClass {@code target} in the list with {@code editedModuleClass}.
     * {@code target} must exist in the list.
     * The ModuleClass identity of {@code editedModuleClass} must not be the same
     * as another existing ModuleClass in the list.
     */
    public void setModuleClass(ModuleClass target, ModuleClass editedModuleClass) {
        requireAllNonNull(target, editedModuleClass);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleClassNotFoundException();
        }

        if (!target.isSameModuleClass(editedModuleClass) && contains(editedModuleClass)) {
            throw new DuplicateModuleClassException();
        }

        internalList.set(index, editedModuleClass);
    }

    public void setModuleClass(UniqueModuleClassList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code moduleClasses}.
     * {@code moduleClasses} must not contain duplicate ModuleClasses.
     */
    public void setModuleClass(List<ModuleClass> moduleClasses) {
        requireAllNonNull(moduleClasses);
        if (!moduleClassesAreUnique(moduleClasses)) {
            throw new DuplicateModuleClassException();
        }

        internalList.setAll(moduleClasses);
    }

    /**
     * Removes the equivalent ModuleClass from the list.
     * The ModuleClass must exist in the list.
     */
    public void remove(ModuleClass toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleClassNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ModuleClass> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ModuleClass> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleClassList // instanceof handles nulls
                && internalList.equals(((UniqueModuleClassList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code moduleClasses} contains only unique ModuleClasses.
     */
    private boolean moduleClassesAreUnique(List<ModuleClass> moduleClasses) {
        for (int i = 0; i < moduleClasses.size() - 1; i++) {
            for (int j = i + 1; j < moduleClasses.size(); j++) {
                if (moduleClasses.get(i).isSameModuleClass(moduleClasses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
