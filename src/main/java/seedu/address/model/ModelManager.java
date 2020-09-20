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
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the Tutor's Pet data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TutorsPet tutorsPet;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given tutorsPet and userPrefs.
     */
    public ModelManager(ReadOnlyTutorsPet tutorsPet, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(tutorsPet, userPrefs);

        logger.fine("Initializing with Tutor's Pet: " + tutorsPet + " and user prefs " + userPrefs);

        this.tutorsPet = new TutorsPet(tutorsPet);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.tutorsPet.getPersonList());
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
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return tutorsPet.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        tutorsPet.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        tutorsPet.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        tutorsPet.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedTutorsPet}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
                && filteredPersons.equals(other.filteredPersons);
    }

}
