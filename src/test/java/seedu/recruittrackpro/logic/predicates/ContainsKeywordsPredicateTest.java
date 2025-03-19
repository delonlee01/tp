package seedu.recruittrackpro.logic.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

public class ContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Object[] firstKeywordsArray = {PREFIX_NAME, new String[]{"Alice", "Bob"}};
        Object[] secondKeywordsArray = {PREFIX_TAG, new String[]{"friends", "neighbour"}};

        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicate(firstKeywordsArray);
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicate(firstKeywordsArray, secondKeywordsArray);
        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContainsKeywordsPredicate firstPredicateCopy = new ContainsKeywordsPredicate(firstKeywordsArray);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicates -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {

        // One name keyword
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_NAME, new String[]{"Alice"}});
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // One tag keyword
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"friend"}});
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));

        // Multiple matching tag keywords
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"friend", "neighbour"}});
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "neighbour").build()));

        // Only one matching tag keyword
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"friend", "developer"}});
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "neighbour").build()));

        // Mixed-case tag keywords
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"fRieNd", "NeiGhBOur"}});
        assertTrue(predicate.test(new PersonBuilder().withTags("friend", "neighbour").build()));
    }

    @Test
    public void test_doesNotContainKeywords_returnsFalse() {

        // Zero keyword
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(new Object[2]);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching name keyword
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_NAME, new String[]{"Alice"}});
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));

        // Non-matching tag keyword
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"friend"}});
        assertFalse(predicate.test(new PersonBuilder().withTags("neighbour").build()));

        // Keywords match phone, email and address, but does not match tag
        predicate =
                new ContainsKeywordsPredicate(new Object[]{PREFIX_TAG, new String[]{"Alice", "12345",
                        "alice@email.com", "Main", "Street"}});
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street")
                .withTags("friend", "neighbour").build()));
    }


    @Test
    public void toStringMethod() {
        Object[] firstKeywordsArray = {PREFIX_NAME, new String[]{"Alice", "Bob"}};
        Object[] secondKeywordsArray = {PREFIX_TAG, new String[]{"friends", "neighbour"}};

        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(firstKeywordsArray, secondKeywordsArray);

        NameContainsKeywordsPredicate namePredicate =
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        TagContainsKeywordsPredicate tagPredicate =
                new TagContainsKeywordsPredicate(Arrays.asList("friends", "neighbour"));

        List<String> predicatesList = List.of(namePredicate.toString(), tagPredicate.toString());

        String expected = ContainsKeywordsPredicate.class.getCanonicalName() + "{predicates=" + predicatesList + "}";
        assertEquals(expected, predicate.toString());
    }

}


