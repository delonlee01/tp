package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRecruitTrackPro;
import seedu.address.model.RecruitTrackPro;
import seedu.address.model.person.Person;

/**
 * An Immutable RecruitTrackPro that is serializable to JSON format.
 */
@JsonRootName(value = "recruittrackpro")
class JsonSerializableRecruitTrackPro {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRecruitTrackPro} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRecruitTrackPro(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyRecruitTrackPro} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRecruitTrackPro}.
     */
    public JsonSerializableRecruitTrackPro(ReadOnlyRecruitTrackPro source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RecruitTrackPro} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RecruitTrackPro toModelType() throws IllegalValueException {
        RecruitTrackPro recruitTrackPro = new RecruitTrackPro();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (recruitTrackPro.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            recruitTrackPro.addPerson(person);
        }
        return recruitTrackPro;
    }

}
