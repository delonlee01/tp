package seedu.recruittrackpro.logic.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private boolean containAll;

    /**
     * Creates a EmailContainsKeywordsPredicate Object.
     *
     * @param keywords List of keywords.
     * @param containAll Boolean to determine if all keywords must be present.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords, boolean containAll) {
        this.keywords = keywords;
        this.containAll = containAll;
    }

    @Override
    public boolean test(Person person) {
        if (containAll) {
            return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherEmailContainsKeywordsPredicate = (EmailContainsKeywordsPredicate) other;
        return keywords.equals(otherEmailContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
