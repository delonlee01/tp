package seedu.address.testutil;

import seedu.address.model.RecruitTrackPro;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building RecruitTrackPro objects.
 * Example usage: <br>
 *     {@code RecruitTrackPro ab = new RecruitTrackProBuilder().withPerson("John", "Doe").build();}
 */
public class RecruitTrackProBuilder {

    private RecruitTrackPro recruitTrackPro;

    public RecruitTrackProBuilder() {
        recruitTrackPro = new RecruitTrackPro();
    }

    public RecruitTrackProBuilder(RecruitTrackPro recruitTrackPro) {
        this.recruitTrackPro = recruitTrackPro;
    }

    /**
     * Adds a new {@code Person} to the {@code RecruitTrackPro} that we are building.
     */
    public RecruitTrackProBuilder withPerson(Person person) {
        recruitTrackPro.addPerson(person);
        return this;
    }

    public RecruitTrackPro build() {
        return recruitTrackPro;
    }
}
