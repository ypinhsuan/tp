package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS1101S_STUDIO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2103T_TUTORIAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.moduleclass.ModuleClass;

public class TypicalModuleClass {

    public static final UUID STUDENT_UUID_1 = UUID.fromString("0c527a3f-8a6f-4c16-b57d-563a9c43cf6b");
    public static final UUID STUDENT_UUID_2 = UUID.fromString("5c09bbdc-b89c-482a-a4b0-32ede6d679e7");

    public static final ModuleClass CS2103T_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2103T_TUTORIAL).withStudentIds(STUDENT_UUID_1, STUDENT_UUID_2).build();

    public static final ModuleClass CS2100_LAB = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2100_LAB).build();

    public static final ModuleClass CS1101S_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS1101S_STUDIO).withStudentIds(STUDENT_UUID_1).build();

    private TypicalModuleClass() {} // Prevents instantiation

    public static List<ModuleClass> getTypicalModuleClasses() {
        return new ArrayList<>(Arrays.asList(CS2103T_TUTORIAL, CS2100_LAB, CS1101S_TUTORIAL));
    }
}
