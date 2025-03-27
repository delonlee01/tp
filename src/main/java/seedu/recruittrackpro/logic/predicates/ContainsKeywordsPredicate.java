package seedu.recruittrackpro.logic.predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

/**
 * Tests if a {@code Person}'s {@code Name} or {@code Tag} matches any of the given keywords.
 * The keywords are matched based on the specified prefix ("n/" for names, "t/" for tags).
 */
public class ContainsKeywordsPredicate implements Predicate<Person> {
    private final Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicatesList;

    /**
     * Takes in Object[] of keywords using the first element to check type of prefix,
     * and adds keywords to an ArrayList as corresponding predicates.
     * Initialises a combinedPredicate using "or" on all predicates.
     */
    //nameKeywords, tagKeywords, addressKeywords, emailKeywords, phoneKeywords
    public ContainsKeywordsPredicate(Object[] ... keywordsArrays) {
        predicatesList = new ArrayList<>();

        for (Object[] keywordsArray : keywordsArrays) {
            if (keywordsArray[1] instanceof String[] && ((String[]) keywordsArray[1]).length > 0) {
                switch (keywordsArray[0].toString()) {
                case "n/":
                    predicatesList.add(new NameContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                case "t/":
                    predicatesList.add(new TagContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                case "a/":
                    predicatesList.add(new AddressContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                case "e/":
                    predicatesList.add(new EmailContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                case "p/":
                    predicatesList.add(new PhoneContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                case "c/":
                    predicatesList.add(new CommentContainsKeywordsPredicate(
                            Arrays.asList((String[]) keywordsArray[1])));
                    break;
                default:
                    break;
                }
            }
        }

        Predicate<Person> result = predicatesList.isEmpty()
                ? new NameContainsKeywordsPredicate(Collections.emptyList()) : predicatesList.get(0);

        for (int i = 1; i < predicatesList.size(); i++) {
            result = result.or(predicatesList.get(i));
        }
        combinedPredicate = result;
    }

    @Override
    public boolean test(Person person) {
        return combinedPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsKeywordsPredicate)) {
            return false;
        }

        ContainsKeywordsPredicate otherContainsKeywordPredicate = (ContainsKeywordsPredicate) other;
        return predicatesList.size() == otherContainsKeywordPredicate.predicatesList.size()
                && predicatesList.containsAll(otherContainsKeywordPredicate.predicatesList);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        List<String> predicatesAsString = new ArrayList<>();

        for (Predicate<Person> predicate : predicatesList) {
            predicatesAsString.add(predicate.toString());
        }

        stringBuilder.add("predicates", predicatesAsString);

        return stringBuilder.toString();
    }
}
