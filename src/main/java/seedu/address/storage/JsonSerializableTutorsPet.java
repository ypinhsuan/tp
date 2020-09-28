package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTutorsPet;
import seedu.address.model.TutorsPet;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * An Immutable TutorsPet that is serializable to JSON format.
 */
@JsonRootName(value = "tutorspet")
class JsonSerializableTutorsPet {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_MODULE_CLASS = "Class list contains duplicate class(es).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedModuleClass> classes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorsPet} with the given students and classes.
     */
    @JsonCreator
    public JsonSerializableTutorsPet(@JsonProperty("students") List<JsonAdaptedStudent> students,
                                     @JsonProperty("classes") List<JsonAdaptedModuleClass> classes) {
        this.students.addAll(students);
        this.classes.addAll(classes);
    }

    /**
     * Converts a given {@code ReadOnlyTutorsPet} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorsPet}.
     */
    public JsonSerializableTutorsPet(ReadOnlyTutorsPet source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
        classes.addAll(source.getModuleClassList().stream()
                .map(JsonAdaptedModuleClass::new).collect(Collectors.toList()));
    }

    /**
     * Converts students into the model's {@code TutorsPet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public void studentsToModelType(TutorsPet tutorsPet) throws IllegalValueException {
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (tutorsPet.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            tutorsPet.addStudent(student);
        }
    }

    /**
     * Converts classes into the model's {@code TutorsPet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public void classesToModelType(TutorsPet tutorsPet) throws IllegalValueException {
        for (JsonAdaptedModuleClass jsonAdaptedModuleClass : classes) {
            ModuleClass moduleClass = jsonAdaptedModuleClass.toModelType();
            if (tutorsPet.hasModuleClass(moduleClass)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE_CLASS);
            }
            tutorsPet.addModuleClass(moduleClass);
        }
    }

    /**
     * Converts this Tutor's Pet into the model's {@code TutorsPet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorsPet toModelType() throws IllegalValueException {
        TutorsPet tutorsPet = new TutorsPet();
        this.studentsToModelType(tutorsPet);
        this.classesToModelType(tutorsPet);
        return tutorsPet;
    }
}
