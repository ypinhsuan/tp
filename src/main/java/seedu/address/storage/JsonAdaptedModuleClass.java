package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.components.name.Name;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Jackson-friendly version of {@link ModuleClass}.
 */
public class JsonAdaptedModuleClass {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ModuleClass's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "ModuleClass's %s field is invalid!";

    private final String name;
    private final List<JsonAdaptedUuid> studentUuids = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModuleClass} with the given class details.
     */
    @JsonCreator
    public JsonAdaptedModuleClass(@JsonProperty("name") String name,
                                  @JsonProperty("studentUuids") List<JsonAdaptedUuid> studentUuids) {
        this.name = name;
        if (studentUuids != null) {
            this.studentUuids.addAll(studentUuids);
        }
    }

    /**
     * Converts a given {@code ModuleClass} into a {@code JsonAdaptedModuleClass} for Jackson use.
     */
    public JsonAdaptedModuleClass(ModuleClass source) {
        name = source.getName().fullName;
        studentUuids.addAll(source.getStudentUuids().stream()
               .map(JsonAdaptedUuid::new)
               .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted class object into the model's {@code ModuleClass} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted class.
     */
    public ModuleClass toModelType() throws IllegalValueException {
        final List<UUID> moduleClassUuids = new ArrayList<>();
        for (JsonAdaptedUuid studentUuid : studentUuids) {
            if (studentUuid == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "studentUuid"));
            }

            // catch invalid UUID
            try {
                String uuidString = studentUuid.getUuidString();
                UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, "studentUuid"));
            }
            moduleClassUuids.add(studentUuid.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Set<UUID> modelUuids = new HashSet<>(moduleClassUuids);

        // TODO: Implement storage for Lesson.
        return new ModuleClass(modelName, modelUuids, new ArrayList<>());
    }
}
