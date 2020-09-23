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
import seedu.address.model.student.Student;

/**
 * An Immutable TutorsPet that is serializable to JSON format.
 */
@JsonRootName(value = "tutorspet")
class JsonSerializableTutorsPet {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTutorsPet} with the given students.
     */
    @JsonCreator
    public JsonSerializableTutorsPet(@JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.students.addAll(students);
    }

    /**
     * Converts a given {@code ReadOnlyTutorsPet} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTutorsPet}.
     */
    public JsonSerializableTutorsPet(ReadOnlyTutorsPet source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Tutor's Pet into the model's {@code TutorsPet} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TutorsPet toModelType() throws IllegalValueException {
        TutorsPet tutorsPet = new TutorsPet();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (tutorsPet.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            tutorsPet.addStudent(student);
        }
        return tutorsPet;
    }

}
