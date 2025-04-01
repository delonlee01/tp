package seedu.recruittrackpro.model;

import javafx.collections.ObservableList;
import seedu.recruittrackpro.model.person.Person;

/**
 * Unmodifiable view of RecruitTrackPro
 */
public interface ReadOnlyRecruitTrackPro {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
