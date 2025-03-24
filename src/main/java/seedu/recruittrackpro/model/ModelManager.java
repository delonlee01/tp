package seedu.recruittrackpro.model;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.recruittrackpro.commons.core.GuiSettings;
import seedu.recruittrackpro.commons.core.LogsCenter;
import seedu.recruittrackpro.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RecruitTrackPro recruitTrackPro;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given recruitTrackPro and userPrefs.
     */
    public ModelManager(ReadOnlyRecruitTrackPro recruitTrackPro, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(recruitTrackPro, userPrefs);

        logger.fine("Initializing with address book: " + recruitTrackPro + " and user prefs " + userPrefs);

        this.recruitTrackPro = new RecruitTrackPro(recruitTrackPro);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.recruitTrackPro.getPersonList());
    }

    public ModelManager() {
        this(new RecruitTrackPro(), new UserPrefs());
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
    public Path getRecruitTrackProFilePath() {
        return userPrefs.getRecruitTrackProFilePath();
    }

    @Override
    public void setRecruitTrackProFilePath(Path recruitTrackProFilePath) {
        requireNonNull(recruitTrackProFilePath);
        userPrefs.setRecruitTrackProFilePath(recruitTrackProFilePath);
    }

    //=========== RecruitTrackPro ================================================================================

    @Override
    public void setRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro) {
        this.recruitTrackPro.resetData(recruitTrackPro);
    }

    @Override
    public ReadOnlyRecruitTrackPro getRecruitTrackPro() {
        return recruitTrackPro;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return recruitTrackPro.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        recruitTrackPro.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        recruitTrackPro.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        recruitTrackPro.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedRecruitTrackPro}
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
    public void switchPersonListSorting() {
        recruitTrackPro.getUniquePersonList().switchSorting();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return recruitTrackPro.equals(otherModelManager.recruitTrackPro)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
