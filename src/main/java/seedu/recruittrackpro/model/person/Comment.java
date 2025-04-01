package seedu.recruittrackpro.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's comment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidComment(String)}
 */
public class Comment {
    public static final String MESSAGE_CONSTRAINTS = "Comments should be within 500 characters";

    public static final String VALIDATION_REGEX = ".{0,500}";

    public final String value;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A valid comment.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        checkArgument(isValidComment(comment), MESSAGE_CONSTRAINTS);
        this.value = comment;
    }

    /**
     * Returns true if a given string is a valid comment.
     */
    public static boolean isValidComment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Comment)) {
            return false;
        }

        Comment otherComment = (Comment) other;
        return value.equals(otherComment.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
