package seedu.recruittrackpro.logic.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Comment} matches any of the keywords given.
 */
public class CommentContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public CommentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getComment().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommentContainsKeywordsPredicate)) {
            return false;
        }

        CommentContainsKeywordsPredicate otherCommentContainsKeywordsPredicate =
                (CommentContainsKeywordsPredicate) other;
        return keywords.equals(otherCommentContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
