package seedu.recruittrackpro.logic.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private boolean containAll;

    public TagContainsKeywordsPredicate(List<String> keywords, boolean containAll) {
        this.keywords = keywords;
        this.containAll = containAll;
    }

    @Override
    public boolean test(Person person) {
        if (containAll) {
            return keywords.stream()
                    .allMatch(keyword -> person.getTags().toStream()
                            .filter(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword))
                            .count() > 0);

        }
        return keywords.stream()
                .flatMap(keyword -> person.getTags().toStream()
                        .filter(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)))
                .anyMatch(tag -> true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
