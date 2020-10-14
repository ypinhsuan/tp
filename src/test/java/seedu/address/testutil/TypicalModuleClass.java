package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2103T_TUTORIAL;
import static seedu.address.testutil.TypicalLesson.LESSON_THU_10_TO_11;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.moduleclass.ModuleClass;

public class TypicalModuleClass {

    /**
     * A {@code ModuleClass} for testing with ALICE and BENSON as students and THU_10_TO_11 lesson.
     */
    public static final ModuleClass CS2103T_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2103T_TUTORIAL)
            .withStudentUuids(ALICE.getUuid(), BENSON.getUuid())
            .withLessons(LESSON_THU_10_TO_11)
            .build();

    /**
     * A {@code ModuleClass} for testing with no students and lessons.
     */
    public static final ModuleClass CS2100_LAB = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2100_LAB).build();

    /**
     * A {@code ModuleClass} for testing with ALICE as student and no lessons.
     */
    public static final ModuleClass CS2100_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2100_TUTORIAL).withStudentUuids(ALICE.getUuid()).build();

    private TypicalModuleClass() {} // prevents instantiation

    public static List<ModuleClass> getTypicalModuleClasses() {
        return new ArrayList<>(Arrays.asList(CS2103T_TUTORIAL, CS2100_LAB, CS2100_TUTORIAL));
    }
}
