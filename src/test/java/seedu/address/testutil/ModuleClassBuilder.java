package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.components.Name;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * A utility class to help with building ModuleClass objects.
 */
public class ModuleClassBuilder {

    public static final String DEFAULT_NAME = "CS1101 Tutorial";

    private Name name;
    private Set<UUID> studentUuids;

    /**
     * Creates a {@code ModuleClassBuilder} with the default details and empty {@code studentIds} list.
     */
    public ModuleClassBuilder() {
        name = new Name(DEFAULT_NAME);
        studentUuids = new HashSet<>();
    }

    /**
     * Initializes the ModuleClassBuilder with the data of {@code moduleClassToCopy}.
     */
    public ModuleClassBuilder(ModuleClass moduleClassToCopy) {
        name = moduleClassToCopy.getName();
        studentUuids = new HashSet<>(moduleClassToCopy.getStudentUuids());
    }

    /**
     * Sets the {@code Name} of the {@code ModuleClass} that we are building.
     */
    public ModuleClassBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Set<UUID>} of the {@code ModuleClass} that we are building.
     * Overwrites any previous content of the {@code Set<UUID>}.
     */
    public ModuleClassBuilder withStudentUuids(UUID ... studentUuids) {
        this.studentUuids = new HashSet<>(Arrays.asList(studentUuids));
        return this;
    }

    public ModuleClass build() {
        return new ModuleClass(name, studentUuids);
    }
}
