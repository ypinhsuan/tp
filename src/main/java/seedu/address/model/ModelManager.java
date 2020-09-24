package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the Tutor's Pet data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TutorsPet tutorsPet;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given tutorsPet and userPrefs.
     */
    public ModelManager(ReadOnlyTutorsPet tutorsPet, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(tutorsPet, userPrefs);

        logger.fine("Initializing with Tutor's Pet: " + tutorsPet + " and user prefs " + userPrefs);

        this.tutorsPet = new TutorsPet(tutorsPet);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.tutorsPet.getStudentList());
    }

    public ModelManager() {
        this(new TutorsPet(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTutorsPetFilePath() {
        return userPrefs.getTutorsPetFilePath();
    }

    @Override
    public void setTutorsPetFilePath(Path tutorsPetFilePath) {
        requireNonNull(tutorsPetFilePath);
        userPrefs.setTutorsPetFilePath(tutorsPetFilePath);
    }

    //=========== TutorsPet ================================================================================

    @Override
    public void setTutorsPet(ReadOnlyTutorsPet tutorsPet) {
        this.tutorsPet.resetData(tutorsPet);
    }

    @Override
    public ReadOnlyTutorsPet getTutorsPet() {
        return tutorsPet;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return tutorsPet.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        tutorsPet.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        tutorsPet.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        tutorsPet.setStudent(target, editedStudent);
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedTutorsPet}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return tutorsPet.equals(other.tutorsPet)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

}
