package seedu.address.testutil;

import seedu.address.model.TutorsPet;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

public class TypicalTutorsPet {

    /**
     * Returns an {@code TutorsPet} with all the typical students and classes.
     */
    public static TutorsPet getTypicalTutorsPet() {
        TutorsPet tp = new TutorsPet();
        for (Student student : TypicalStudent.getTypicalStudents()) {
            tp.addStudent(student);
        }
        for (ModuleClass moduleClass : TypicalModuleClass.getTypicalModuleClasses()) {
            tp.addModuleClass(moduleClass);
        }
        return tp;
    }
}
