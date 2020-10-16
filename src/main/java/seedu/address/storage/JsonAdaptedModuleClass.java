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
import seedu.address.model.lesson.Lesson;
import seedu.address.model.moduleclass.ModuleClass;

/**
 * Jackson-friendly version of {@link ModuleClass}.
 */
public class JsonAdaptedModuleClass {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ModuleClass's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "ModuleClass's %s field is invalid!";
    public static final String DUPLICATE_LESSON_MESSAGE = "Class contains duplicate lesson(s).";
    public static final String STUDENT_UUID_FIELD = "student uuid";

    private final String name;
    private final List<JsonAdaptedUuid> studentUuids = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedModuleClass} with the given class details.
     */
    @JsonCreator
    public JsonAdaptedModuleClass(@JsonProperty("name") String name,
                                  @JsonProperty("studentUuids") List<JsonAdaptedUuid> studentUuids,
                                  @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.name = name;
        if (studentUuids != null) {
            this.studentUuids.addAll(studentUuids);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
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
        lessons.addAll(source.getLessons().stream()
                .map(JsonAdaptedLesson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Gets a {@code List} of {@code UUID}s from {@code studentUuids}.
     *
     * @throws IllegalValueException if any of the {@code UUID}s are null or invalid.
     */
    private List<UUID> getUuidList() throws IllegalValueException {
        List<UUID> uuidList = new ArrayList<>();
        for (JsonAdaptedUuid studentUuid : studentUuids) {
            if (studentUuid == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, STUDENT_UUID_FIELD));
            }

            // catch invalid UUID
            try {
                String uuidString = studentUuid.getUuidString();
                UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, STUDENT_UUID_FIELD));
            }
            uuidList.add(studentUuid.toModelType());
        }
        return uuidList;
    }

    /**
     * Checks if a {@code lesson} already exists in a list.
     * Duplicates are detected by calling {@code isSameLesson} method in {@code Lesson}.
     * Returns true if there is a duplicate.
     */
    private boolean hasDuplicateLessons(List<Lesson> lessons, Lesson lessonToCheck) {
        for (Lesson lesson: lessons) {
            if (lessonToCheck.isSameLesson(lesson)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a {@code List} of {@code Lesson}s from {@code lessons}.
     * Removes any duplicate {@code Lesson}s, as ascertained by {@code isSameLesson}.
     *
     * @throws IllegalValueException if any of the {@code Lesson}s are null.
     */
    public List<Lesson> getLessonList() throws IllegalValueException {
        List<Lesson> lessonList = new ArrayList<>();
        for (JsonAdaptedLesson jsonLesson : lessons) {
            if (jsonLesson == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Lesson.class.getSimpleName()));
            }

            if (hasDuplicateLessons(lessonList, jsonLesson.toModelType())) {
                throw new IllegalValueException(DUPLICATE_LESSON_MESSAGE);
            } else {
                lessonList.add(jsonLesson.toModelType());
            }
        }
        return lessonList;
    }

    /**
     * Converts this Jackson-friendly adapted class object into the model's {@code ModuleClass} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted class.
     */
    public ModuleClass toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        List<UUID> moduleClassUuids = getUuidList();
        final Set<UUID> modelUuids = new HashSet<>(moduleClassUuids);

        final List<Lesson> lessonList = getLessonList();

        return new ModuleClass(modelName, modelUuids, lessonList);
    }
}
