package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS1101S_STUDIO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2100_LAB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2103T_TUTORIAL;
import static seedu.address.testutil.TypicalStudent.ALICE;
import static seedu.address.testutil.TypicalStudent.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.moduleclass.ModuleClass;

public class TypicalModuleClass {

    public static final UUID ALICE_UUID = ALICE.getUuid();
    public static final UUID BENSON_UUID = BENSON.getUuid();

    public static final ModuleClass CS2103T_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2103T_TUTORIAL).withStudentUuids(ALICE_UUID, BENSON_UUID).build();

    public static final ModuleClass CS2100_LAB = new ModuleClassBuilder()
            .withName(VALID_NAME_CS2100_LAB).build();

    public static final ModuleClass CS1101S_TUTORIAL = new ModuleClassBuilder()
            .withName(VALID_NAME_CS1101S_STUDIO).withStudentUuids(ALICE_UUID).build();

    private TypicalModuleClass() {} // Prevents instantiation

    public static List<ModuleClass> getTypicalModuleClasses() {
        return new ArrayList<>(Arrays.asList(CS2103T_TUTORIAL, CS2100_LAB, CS1101S_TUTORIAL));
    }
}