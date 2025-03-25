package seedu.recruittrackpro.model.person;

import static seedu.recruittrackpro.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Objects;

import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Comparators
    public static final Comparator<Person> PERSON_COMPARATOR_ASCENDING = Comparator
            .comparing((Person person) -> person.getName().toString().toLowerCase())
            .thenComparing((Person person) -> person.getPhone().toString());
    public static final Comparator<Person> PERSON_COMPARATOR_DESCENDING =
            PERSON_COMPARATOR_ASCENDING.reversed();

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Tags tags;
    private final Comment comment;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Tags tags, Comment comment) {
        requireAllNonNull(name, phone, email, address, tags, comment);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = tags;
        this.comment = comment;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Tags getTags() {
        return tags;
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * Returns true if both persons have the same name regardless of case-sensitivity.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == null) {
            return false;
        }
        if (this == otherPerson) {
            return true;
        }

        String thisName = getName().fullName;
        String otherName = otherPerson.getName().fullName;

        return thisName.equalsIgnoreCase(otherName) && getPhone().equals(otherPerson.getPhone());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && comment.equals(otherPerson.comment);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, comment);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("comment", comment)
                .toString();
    }

}
