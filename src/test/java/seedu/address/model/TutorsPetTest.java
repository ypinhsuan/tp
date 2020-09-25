package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AVERAGE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModuleClass.CS2103T_TUTORIAL;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalTutorsPet.getTypicalTutorsPet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.moduleclass.exceptions.DuplicateModuleClassException;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.ModuleClassBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalModuleClass;
import seedu.address.testutil.TypicalStudent;

public class TutorsPetTest {

    private final TutorsPet tutorsPet = new TutorsPet();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tutorsPet.getStudentList());
        assertEquals(Collections.emptyList(), tutorsPet.getModuleClassList());
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

    //// student-related tests

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_AVERAGE)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        List<ModuleClass> defaultModuleClasses = TypicalModuleClass.getTypicalModuleClasses();
        TutorsPetStub newData = new TutorsPetStub(newStudents, defaultModuleClasses);

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

    //// moduleClass-related tests

    @Test
    public void resetData_withDuplicateModuleClass_throwsDuplicateModuleClassException() {
        // Two moduleClasses with the same identity fields
        ModuleClass editedCS2103T = new ModuleClassBuilder(CS2103T_TUTORIAL).withStudentIds().build();
        List<Student> defaultStudents = TypicalStudent.getTypicalStudents();
        List<ModuleClass> newModuleClasses = Arrays.asList(CS2103T_TUTORIAL, editedCS2103T);
        TutorsPetStub newData = new TutorsPetStub(defaultStudents, newModuleClasses);

        assertThrows(DuplicateModuleClassException.class, () -> tutorsPet.resetData(newData));
    }

    @Test
    public void hasModuleClass_nullModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tutorsPet.hasModuleClass(null));
    }

    @Test
    public void hasModuleClass_moduleClassNotInTutorsPet_returnsFalse() {
        assertFalse(tutorsPet.hasModuleClass(CS2103T_TUTORIAL));
    }

    @Test
    public void hasModuleClass_moduleClassInTutorsPet_returnsTrue() {
        tutorsPet.addModuleClass(CS2103T_TUTORIAL);
        assertTrue(tutorsPet.hasModuleClass(CS2103T_TUTORIAL));
    }

    @Test
    public void hasModuleClass_moduleClassWithSameIdentityFieldsInTutorsPet_returnsTrue() {
        tutorsPet.addModuleClass(CS2103T_TUTORIAL);
        ModuleClass editedModuleClass = new ModuleClassBuilder(CS2103T_TUTORIAL).withStudentIds().build();
        assertTrue(tutorsPet.hasModuleClass(editedModuleClass));
    }

    @Test
    public void getModuleClassList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tutorsPet.getModuleClassList().remove(0));
    }

    /**
     * A stub ReadOnlyTutorsPet whose students list can violate interface constraints.
     */
    private static class TutorsPetStub implements ReadOnlyTutorsPet {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<ModuleClass> classes = FXCollections.observableArrayList();

        TutorsPetStub(Collection<Student> students, Collection<ModuleClass> classes) {
            this.students.setAll(students);
            this.classes.setAll(classes);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<ModuleClass> getModuleClassList() {
            return classes;
        }
    }

}
