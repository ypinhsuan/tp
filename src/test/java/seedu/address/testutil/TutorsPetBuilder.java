package seedu.address.testutil;

import seedu.address.model.TutorsPet;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building TutorsPet objects.
 * Example usage: <br>
 *     {@code TutorsPet ab = new TutorsPetBuilder().withPerson("John", "Doe").build();}
 */
public class TutorsPetBuilder {

    private TutorsPet tutorsPet;

    public TutorsPetBuilder() {
        tutorsPet = new TutorsPet();
    }

    public TutorsPetBuilder(TutorsPet tutorsPet) {
        this.tutorsPet = tutorsPet;
    }

    /**
     * Adds a new {@code Student} to the {@code TutorsPet} that we are building.
     */
    public TutorsPetBuilder withPerson(Student student) {
        tutorsPet.addStudent(student);
        return this;
    }

    public TutorsPet build() {
        return tutorsPet;
    }
}
