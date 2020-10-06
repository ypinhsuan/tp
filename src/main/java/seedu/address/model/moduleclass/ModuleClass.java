package seedu.address.model.moduleclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.components.name.HasName;
import seedu.address.model.components.name.Name;
import seedu.address.model.student.Student;

/**
 * Represents a Class.
 * Contains information on the students enrolled in this class identified by their {@code UUID}.
 * Students must exist in the student manager.
 * Guarantees: details are present and not null, field values are immutable.
 *
 * @see Student#getUuid()
 */
public class ModuleClass implements HasName {

    // identity fields
    private final Name name;

    // data fields
    private final Set<UUID> studentUuids = new HashSet<>();

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
    public ModuleClass(Name name, Set<UUID> studentUuids) {
        requireAllNonNull(name, studentUuids);

        this.name = name;
        this.studentUuids.addAll(studentUuids);
    }

    @Override
    public Name getName() {
        return name;
    }

    /**
     * Returns an immutable set of student {@code UUID}s, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<UUID> getStudentUuids() {
        return Collections.unmodifiableSet(studentUuids);
    }

    /**
     * Returns true if the class contains an equivalent student {@code UUID} as the given argument.
     *
     * @throws NullPointerException if the given student {@code UUID} is null.
     */
    public boolean hasStudentUuid(UUID toCheck) throws NullPointerException {
        return studentUuids.stream().anyMatch(toCheck::equals);
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
                && otherModuleClass.getStudentUuids().equals(getStudentUuids());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentUuids);
    }

    @Override
    public String toString() {
        return getName().toString();
    }
}
