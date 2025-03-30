package seedu.recruittrackpro.logic.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        EmailContainsKeywordsPredicate predicate1 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johndoe@example.com"}), false);
        EmailContainsKeywordsPredicate predicate2 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johndoe@example.com"}), false);
        EmailContainsKeywordsPredicate predicate3 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"johnny@example.com"}), false);
        assertTrue(predicate1.equals(predicate1));
        assertTrue(predicate1.equals(predicate2));
        assertFalse(predicate1.equals(predicate3));
        assertFalse(predicate1.equals("not a predicate"));
        assertFalse(predicate1.equals(null));
    }

    @Test
    public void test_containEmailKeywords_returnsTrue() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"keyword1@example.com"}), false);
        assertTrue(predicate.test(new PersonBuilder().withEmail("keyword1@example.com").build()));
    }

    @Test
    public void test_doesNotContainEmailKeywords_returnsFalse() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"keyword1@example.com"}), false);
        assertFalse(predicate.test(new PersonBuilder().withEmail("keyword2@example.com").build()));

        EmailContainsKeywordsPredicate predicate2 =
                new EmailContainsKeywordsPredicate(List.of(new String[]{"keyword1@example.com"}), true);
        assertFalse(predicate2.test(new PersonBuilder().withEmail("keyword2@example.com").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1@example.com", "keyword2@example.com");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords, false);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
