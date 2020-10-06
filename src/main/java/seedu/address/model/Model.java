package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<ModuleClass> PREDICATE_SHOW_ALL_MODULE_CLASS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Tutor's Pet file path.
     */
    Path getTutorsPetFilePath();

    /**
     * Sets the user prefs' Tutor's Pet file path.
     */
    void setTutorsPetFilePath(Path tutorsPetFilePath);

    /**
     * Replaces Tutor's Pet data with the data in {@code tutorsPet}.
     */
    void setTutorsPet(ReadOnlyTutorsPet tutorsPet);

    /** Returns the TutorsPet */
    ReadOnlyTutorsPet getTutorsPet();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the application.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * Also deletes the {@code Student}'s {@code UUID} in all {@code ModuleClass}es.
     * The student must exist in the application.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the application.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the application.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the
     * application.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Deletes all {@code Student}s.
     * Additionally, removes all {@code Student UUID}s in each {@code ModuleClass}.
     */
    void deleteAllStudents();

    /**
     * Returns an unmodifiable view of the filtered student list.
     */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Returns true if a ModuleClass with the same identity as {@code moduleClass} exists in the application.
     */
    boolean hasModuleClass(ModuleClass moduleClass);

    /**
     * Deletes the given {@code ModuleClass}.
     * The {@code ModuleClass} must exist in the application.
     */
    void deleteModuleClass(ModuleClass target);

    /**
     * Adds the given {@code ModuleClass}.
     * {@code moduleClass} must not already exist in the application.
     */
    void addModuleClass(ModuleClass moduleClass);

    /**
     * Replaces the given {@code ModuleClass} {@code target} with {@code editedModuleClass}.
     * {@code target} must exist in the application.
     * The {@code ModuleClass} identity of {@code editedModuleClass} must not be the same as another existing
     * {@code ModuleClass} in the application.
     */
    void setModuleClass(ModuleClass target, ModuleClass editedModuleClass);

    /**
     * Deletes all {@code ModuleClass}es.
     */
    void deleteAllModuleClasses();

    /**
     * Returns an unmodifiable view of the filtered {@code ModuleClass} list.
     */
    ObservableList<ModuleClass> getFilteredModuleClassList();

    /**
     * Updates the filter of the filtered {@code ModuleClass} list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleClassList(Predicate<ModuleClass> predicate);
}
