package seedu.recruittrackpro.testutil;

import java.util.Arrays;

import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.person.Address;
import seedu.recruittrackpro.model.person.Comment;
import seedu.recruittrackpro.model.person.Email;
import seedu.recruittrackpro.model.person.Name;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.person.Phone;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_COMMENT = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Tags tags;
    private Comment comment;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new Tags();
        comment = new Comment(DEFAULT_COMMENT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = personToCopy.getTags();
        comment = personToCopy.getComment();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Tags} of the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tagNames) {
        try {
            this.tags = new Tags(Arrays.asList(tagNames));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid tag provided: " + e.getMessage(), e);
        }
        return this;
    }

    /**
     * Adds tag(s) to the current {@code Tags} of the person being built.
     */
    public PersonBuilder addTags(String... tagNames) {
        try {
            Tags newTags = new Tags(Arrays.asList(tagNames));
            this.tags = this.tags.combineTags(newTags);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid tag provided: " + e.getMessage(), e);
        }
        return this;
    }

    /**
     * Removes tag(s) from the current {@code Tags} of the person being built.
     */
    public PersonBuilder removeTags(String... tagNames) {
        try {
            Tags tagsToRemove = new Tags(Arrays.asList(tagNames));
            this.tags = this.tags.excludeTags(tagsToRemove);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid tag provided: " + e.getMessage(), e);
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Comment} of the {@code Person} that we are building.
     */
    public PersonBuilder withComment(String comment) {
        this.comment = new Comment(comment);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, comment);
    }

}
