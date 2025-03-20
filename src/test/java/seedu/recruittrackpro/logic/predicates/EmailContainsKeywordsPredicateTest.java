package seedu.recruittrackpro.logic.predicates;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class EmailContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        EmailContainsKeywordsPredicate predicate1 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johndoe@example.com"}));
        EmailContainsKeywordsPredicate predicate2 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johndoe@example.com"}));
        EmailContainsKeywordsPredicate predicate3 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johnny@example.com"}));
        assertEquals(predicate1, predicate2);
        assertNotEquals(predicate1, predicate3);
    }

    @Test
    public void test_containEmailKeywords_returnsTrue() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"keyword1@example.com"}));
        assertTrue(predicate.test(new PersonBuilder().withEmail("keyword1@example.com").build()));
    }

    @Test
    public void test_doesNotContainEmailKeywords_returnsFalse() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"keyword1@example.com"}));
        assertFalse(predicate.test(new PersonBuilder().withEmail("keyword2@example.com").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1@example.com", "keyword2@example.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
