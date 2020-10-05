package seedu.address.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTutorsPet;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getTutorsPetFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTutorsPetFilePath(Path tutorsPetFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTutorsPet(ReadOnlyTutorsPet newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTutorsPet getTutorsPet() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAllStudents() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModuleClass(ModuleClass moduleClass) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModuleClass(ModuleClass moduleClass) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModuleClass(ModuleClass target, ModuleClass editedModuleClass) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModuleClass(ModuleClass target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAllModuleClasses() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<ModuleClass> getFilteredModuleClassList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredModuleClassList(Predicate<ModuleClass> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}
