package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AVERAGE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.getTypicalTutorsPet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.StudentBuilder;

public class TutorsPetTest {

    private final TutorsPet tutorsPet = new TutorsPet();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tutorsPet.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorsPet.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTutorsPet_replacesData() {
        TutorsPet newData = getTypicalTutorsPet();
        tutorsPet.resetData(newData);
        assertEquals(newData, tutorsPet);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_AVERAGE)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        TutorsPetStub newData = new TutorsPetStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> tutorsPet.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorsPet.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInTutorsPet_returnsFalse() {
        assertFalse(tutorsPet.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInTutorsPet_returnsTrue() {
        tutorsPet.addStudent(ALICE);
        assertTrue(tutorsPet.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInTutorsPet_returnsTrue() {
        tutorsPet.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_AVERAGE)
                .build();
        assertTrue(tutorsPet.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tutorsPet.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyTutorsPet whose students list can violate interface constraints.
     */
    private static class TutorsPetStub implements ReadOnlyTutorsPet {
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        TutorsPetStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
