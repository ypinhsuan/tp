package tutorspet.model.moduleclass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorspet.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import tutorspet.model.moduleclass.exceptions.DuplicateModuleClassException;
import tutorspet.model.moduleclass.exceptions.ModuleClassNotFoundException;
import tutorspet.testutil.ModuleClassBuilder;
import tutorspet.testutil.TypicalModuleClass;
import tutorspet.testutil.TypicalStudent;

public class UniqueModuleClassListTest {

    private final UniqueModuleClassList uniqueModuleClassList = new UniqueModuleClassList();

    @Test
    public void contains_nullModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList.contains(null));
    }

    @Test
    public void contains_moduleClassNotInList_returnsFalse() {
        assertFalse(uniqueModuleClassList.contains(TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void contains_moduleClassInList_returnsTrue() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        assertTrue(uniqueModuleClassList.contains(TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void contains_moduleClassWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        ModuleClass editedCs2103t = new ModuleClassBuilder(TypicalModuleClass.CS2103T_TUTORIAL)
                .withStudentUuids(TypicalStudent.AMY.getUuid()).build();
        assertTrue(uniqueModuleClassList.contains(editedCs2103t));
    }

    @Test
    public void add_nullModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList.add(null));
    }

    @Test
    public void add_duplicateModuleClass_throwsDuplicateModuleClassException() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        assertThrows(DuplicateModuleClassException.class, () -> uniqueModuleClassList
                .add(TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void add_moduleClassWithSameIdentityFieldsInList_throwsDuplicateModuleClassException() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        ModuleClass editedCs2103t = new ModuleClassBuilder(TypicalModuleClass.CS2103T_TUTORIAL)
                .withStudentUuids(TypicalStudent.AMY.getUuid()).build();
        assertThrows(DuplicateModuleClassException.class, () -> uniqueModuleClassList.add(editedCs2103t));
    }

    @Test
    public void setModuleClass_nullTargetModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList
                .setModuleClass(null, TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void setModuleClass_nullEditedModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList
                .setModuleClass(TypicalModuleClass.CS2103T_TUTORIAL, null));
    }

    @Test
    public void setModuleClass_targetModuleClassNotInList_throwsModuleClassNotFoundException() {
        assertThrows(ModuleClassNotFoundException.class, ()
            -> uniqueModuleClassList.setModuleClass(TypicalModuleClass.CS2103T_TUTORIAL,
                TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void setModuleClass_moduleClassWithSameIdentityFieldsInList_throwsDuplicateModuleClassException() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        ModuleClass editedClass = new ModuleClassBuilder(TypicalModuleClass.CS2100_LAB)
                .withStudentUuids(TypicalStudent.AMY.getUuid()).build();
        assertThrows(DuplicateModuleClassException.class, () ->
                uniqueModuleClassList.setModuleClass(TypicalModuleClass.CS2103T_TUTORIAL, editedClass));
    }

    @Test
    public void remove_nullModuleClass_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList.remove(null));
    }

    @Test
    public void remove_moduleClassDoesNotExist_throwsModuleClassNotFoundException() {
        assertThrows(ModuleClassNotFoundException.class, () -> uniqueModuleClassList
                .remove(TypicalModuleClass.CS2103T_TUTORIAL));
    }

    @Test
    public void remove_existingModuleClass_removesModuleClass() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        uniqueModuleClassList.remove(TypicalModuleClass.CS2103T_TUTORIAL);
        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void removeUuid_nullUuid_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList.removeUuid(null));
    }

    @Test
    public void removeUuid_existingUuid_updatesModuleClasses() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        UUID uuidToRemove = TypicalModuleClass.CS2103T_TUTORIAL.getStudentUuids().iterator().next();
        uniqueModuleClassList.removeUuid(uuidToRemove);

        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        Set<UUID> modifiedUuids = new HashSet<>(TypicalModuleClass.CS2103T_TUTORIAL.getStudentUuids());
        modifiedUuids.remove(uuidToRemove);
        ModuleClass modifiedModuleClass = new ModuleClass(TypicalModuleClass.CS2103T_TUTORIAL.getName(), modifiedUuids,
                TypicalModuleClass.CS2103T_TUTORIAL.getLessons());
        expectedUniqueModuleClassList.add(modifiedModuleClass);

        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void removeUuid_nonExistingUuid_sameModuleClasses() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        UUID uuidToRemove = UUID.randomUUID();
        uniqueModuleClassList.removeUuid(uuidToRemove);

        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        expectedUniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);

        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void removeAllStudentUuids() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        uniqueModuleClassList.removeAllStudentUuids();
        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        expectedUniqueModuleClassList.add(new ModuleClassBuilder(TypicalModuleClass.CS2103T_TUTORIAL)
                .withStudentUuids().build());
        expectedUniqueModuleClassList.add(new ModuleClassBuilder(TypicalModuleClass.CS2100_LAB)
                .withStudentUuids().build());

        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void setModuleClass_nullUniqueModuleClassList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> uniqueModuleClassList.setModuleClass((UniqueModuleClassList) null));
    }

    @Test
    public void setModuleClass_uniqueModuleClassList_replacesOwnListWithProvidedUniqueModuleClassList() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        expectedUniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        uniqueModuleClassList.setModuleClass(expectedUniqueModuleClassList);
        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void setModuleClass_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleClassList
                .setModuleClass((List<ModuleClass>) null));
    }

    @Test
    public void setModuleClass_list_replacesOwnListWithProvidedList() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        List<ModuleClass> moduleClassList = Collections.singletonList(TypicalModuleClass.CS2100_LAB);
        uniqueModuleClassList.setModuleClass(moduleClassList);
        UniqueModuleClassList expectedUniqueModuleClassList = new UniqueModuleClassList();
        expectedUniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);

        assertEquals(expectedUniqueModuleClassList, uniqueModuleClassList);
    }

    @Test
    public void setModuleClass_listWithDuplicateModuleClasses_throwsDuplicateModuleClassException() {
        List<ModuleClass> listWithDuplicateModuleClasses = Arrays.asList(TypicalModuleClass.CS2103T_TUTORIAL,
                TypicalModuleClass.CS2103T_TUTORIAL);
        assertThrows(DuplicateModuleClassException.class, ()
            -> uniqueModuleClassList.setModuleClass(listWithDuplicateModuleClasses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueModuleClassList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);

        // same internal list -> returns true
        UniqueModuleClassList uniqueModuleClassListCopy = new UniqueModuleClassList();
        uniqueModuleClassListCopy.add(TypicalModuleClass.CS2100_LAB);
        assertTrue(uniqueModuleClassList.equals(uniqueModuleClassListCopy));

        // same object -> returns true
        assertTrue(uniqueModuleClassList.equals(uniqueModuleClassList));

        // null -> returns false
        assertFalse(uniqueModuleClassList.equals(null));

        // different type -> returns false
        assertFalse(uniqueModuleClassList.equals(5));

        // different internal list -> returns false
        UniqueModuleClassList otherUniqueModuleClassList = new UniqueModuleClassList();
        otherUniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        assertFalse(uniqueModuleClassList.equals(otherUniqueModuleClassList));
    }

    @Test
    public void hashCode_sameContents_sameHashCode() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        UniqueModuleClassList uniqueModuleClassListCopy = new UniqueModuleClassList();
        uniqueModuleClassListCopy.add(TypicalModuleClass.CS2100_LAB);
        assertNotSame(uniqueModuleClassListCopy, uniqueModuleClassList);
        assertTrue(uniqueModuleClassList.hashCode() == uniqueModuleClassListCopy.hashCode());
    }

    @Test
    public void hashCode_differentContents_differentHashCode() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        UniqueModuleClassList uniqueModuleClassListCopy = new UniqueModuleClassList();
        uniqueModuleClassListCopy.add(TypicalModuleClass.CS2103T_TUTORIAL);
        assertNotSame(uniqueModuleClassListCopy, uniqueModuleClassList);
        assertFalse(uniqueModuleClassList.hashCode() == uniqueModuleClassListCopy.hashCode());
    }

    @Test
    public void hashCode_changeInContents_differentHashCode() {
        uniqueModuleClassList.add(TypicalModuleClass.CS2100_LAB);
        int hash = uniqueModuleClassList.hashCode();
        uniqueModuleClassList.add(TypicalModuleClass.CS2103T_TUTORIAL);
        assertFalse(uniqueModuleClassList.hashCode() == hash);
    }
}
