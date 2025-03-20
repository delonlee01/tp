package seedu.recruittrackpro.logic.predicates;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        PhoneContainsKeywordsPredicate predicate1 =
                new PhoneContainsKeywordsPredicate(List.of(new String[]{"12345678"}));
        PhoneContainsKeywordsPredicate predicate2 =
                new PhoneContainsKeywordsPredicate(List.of(new String[]{"12345678"}));
        PhoneContainsKeywordsPredicate predicate3 =
                new PhoneContainsKeywordsPredicate(List.of(new String[]{"23456789"}));
        assertEquals(predicate1, predicate2);
        assertNotEquals(predicate1, predicate3);
    }

    @Test
    public void test_containPhoneKeywords_returnsTrue() {
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(List.of(new String[]{"12345678"}));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_doesNotContainPhoneKeywords_returnsFalse() {
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(List.of(new String[]{"12345678"}));
        assertFalse(predicate.test(new PersonBuilder().withPhone("23456789").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("12345678", "23456789");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(keywords);

        String expected = PhoneContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
