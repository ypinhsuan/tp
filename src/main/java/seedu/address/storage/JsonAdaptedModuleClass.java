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
import seedu.address.model.components.Name;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Jackson-friendly version of {@link ModuleClass}.
 */
public class JsonAdaptedModuleClass {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ModuleClass's %s field is missing!";

    private final String name;
    private final List<JsonAdaptedUuid> studentIds = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModuleClass} with the given moduleClass details.
     */
    @JsonCreator
    public JsonAdaptedModuleClass(@JsonProperty("name") String name,
                                  @JsonProperty("studentIds") List<JsonAdaptedUuid> studentIds) {
        this.name = name;
        if (studentIds != null) {
            this.studentIds.addAll(studentIds);
        }
    }

    /**
     * Converts a given {@code ModuleClass} into this class for Jackson use.
     */
    public JsonAdaptedModuleClass(ModuleClass source) {
        name = source.getName().fullName;
        studentIds.addAll(source.getStudentIds().stream()
               .map(JsonAdaptedUuid::new)
               .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted moduleClass object into the model's {@code ModuleClass} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted moduleClass.
     */
    public ModuleClass toModelType() throws IllegalValueException {
        final List<UUID> moduleClassUuids = new ArrayList<>();
        for (JsonAdaptedUuid studentId : studentIds) {
            if (studentId == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "studentId"));
            }
            moduleClassUuids.add(studentId.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        final Set<UUID> modelUuids = new HashSet<>(moduleClassUuids);

        return new ModuleClass(modelName, modelUuids);
    }
}
