package seedu.recruittrackpro.logic.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private boolean containAll;

    public PhoneContainsKeywordsPredicate(List<String> keywords, boolean containAll) {
        this.keywords = keywords;
        this.containAll = containAll;
    }

    @Override
    public boolean test(Person person) {
        if (containAll) {
            return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPhoneContainsKeywordsPredicate = (PhoneContainsKeywordsPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
