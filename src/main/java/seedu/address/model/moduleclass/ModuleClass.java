package seedu.address.model.moduleclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.components.HasName;
import seedu.address.model.components.Name;

/**
 * Represents a Class.
 * Contains information on the students enrolled in this class identified by their unique ID.
 * Guarantees: details are present and not null, field values are immutable.
 */
public class ModuleClass implements HasName {

    // Identity fields
    private final Name name;

    // Data fields
    private final Set<UUID> studentIds = new HashSet<>();

    /**
     * Name must be present and not null.
     */
    public ModuleClass(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    /**
     * Every field must be present and not null.
     */
    public ModuleClass(Name name, Set<UUID> studentIds) {
        requireAllNonNull(name, studentIds);
        this.name = name;
        this.studentIds.addAll(studentIds);
    }

    @Override
    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable set of student {@code UUID}s, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<UUID> getStudentIds() {
        return Collections.unmodifiableSet(studentIds);
    }

    /**
     * Returns true if both classes have the same name.
     * This defines a weaker notion of equality between two classes.
     */
    public boolean isSameModuleClass(ModuleClass otherModuleClass) {
        if (otherModuleClass == this) {
            return true;
        }

        return otherModuleClass != null && otherModuleClass.getName().equals(getName());
    }

    /**
     * Returns true if both classes have the same name and students.
     * This defines a stronger notion of equality between two classes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleClass)) {
            return false;
        }

        ModuleClass otherModuleClass = (ModuleClass) other;
        return otherModuleClass.getName().equals(getName())
                && otherModuleClass.getStudentIds().equals(getStudentIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentIds);
    }

    @Override
    public String toString() {
        return getName().toString();
    }
}
