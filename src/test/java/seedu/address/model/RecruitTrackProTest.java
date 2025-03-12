package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class RecruitTrackProTest {

    private final RecruitTrackPro recruitTrackPro = new RecruitTrackPro();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), recruitTrackPro.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recruitTrackPro.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyRecruitTrackPro_replacesData() {
        RecruitTrackPro newData = getTypicalRecruitTrackPro();
        recruitTrackPro.resetData(newData);
        assertEquals(newData, recruitTrackPro);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        RecruitTrackProStub newData = new RecruitTrackProStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> recruitTrackPro.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recruitTrackPro.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInRecruitTrackPro_returnsFalse() {
        assertFalse(recruitTrackPro.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInRecruitTrackPro_returnsTrue() {
        recruitTrackPro.addPerson(ALICE);
        assertTrue(recruitTrackPro.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInRecruitTrackPro_returnsTrue() {
        recruitTrackPro.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(recruitTrackPro.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> recruitTrackPro.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = RecruitTrackPro.class.getCanonicalName() + "{persons=" + recruitTrackPro.getPersonList() + "}";
        assertEquals(expected, recruitTrackPro.toString());
    }

    /**
     * A stub ReadOnlyRecruitTrackPro whose persons list can violate interface constraints.
     */
    private static class RecruitTrackProStub implements ReadOnlyRecruitTrackPro {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        RecruitTrackProStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
