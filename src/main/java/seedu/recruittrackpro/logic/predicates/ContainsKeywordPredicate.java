package seedu.recruittrackpro.logic.predicates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.model.person.Person;

public class ContainsKeywordPredicate implements Predicate<Person> {
    private final Predicate<Person> combinedPredicate;
    private final List<Predicate<Person>> predicatesList;

    public ContainsKeywordPredicate(Object[] ... keywordsArrays) {
        predicatesList = new ArrayList<>();

        for (Object[] keywordsArray : keywordsArrays) {
            if (((String[])keywordsArray[1]).length > 0)
                switch (keywordsArray[0].toString()) {
                case "n/":
                    predicatesList.add(new NameContainsKeywordsPredicate(
                            Arrays.asList((String[])keywordsArray[1])));
                case "t/":
                    predicatesList.add(new TagContainsKeywordsPredicate(
                            Arrays.asList((String[])keywordsArray[1])));
            }
        }

        Predicate<Person> result = predicatesList.get(0);
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
        if (!(other instanceof ContainsKeywordPredicate)) {
            return false;
        }

        ContainsKeywordPredicate otherContainsKeywordPredicate = (ContainsKeywordPredicate) other;
        return predicatesList.size() == otherContainsKeywordPredicate.predicatesList.size()
                && predicatesList.containsAll(otherContainsKeywordPredicate.predicatesList);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);

        for (Predicate<Person> predicate : predicatesList) {
            stringBuilder.add("predicates", predicate.toString());
        }

        return stringBuilder.toString();
    }
}
