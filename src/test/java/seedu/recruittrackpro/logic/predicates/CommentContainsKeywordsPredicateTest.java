package seedu.recruittrackpro.logic.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

public class CommentContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        CommentContainsKeywordsPredicate predicate1 =
                new CommentContainsKeywordsPredicate(List.of(new String[]{"hello"}));
        CommentContainsKeywordsPredicate predicate2 =
                new CommentContainsKeywordsPredicate(List.of(new String[]{"hello"}));
        CommentContainsKeywordsPredicate predicate3 =
                new CommentContainsKeywordsPredicate(List.of(new String[]{"bye"}));
        assertTrue(predicate1.equals(predicate1));
        assertTrue(predicate1.equals(predicate2));
        assertFalse(predicate1.equals(predicate3));
        assertFalse(predicate1.equals("not a predicate"));
        assertFalse(predicate1.equals(null));
    }

    @Test
    public void test_containCommentKeywords_returnsTrue() {
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(List.of(new String[]{"hello"}));
        assertTrue(predicate.test(new PersonBuilder().withComment("hello").build()));
    }

    @Test
    public void test_doesNotContainCommentKeywords_returnsFalse() {
        CommentContainsKeywordsPredicate predicate =
                new CommentContainsKeywordsPredicate(List.of(new String[]{"hello"}));
        assertFalse(predicate.test(new PersonBuilder().withComment("bye").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("hello", "bye");
        CommentContainsKeywordsPredicate predicate = new CommentContainsKeywordsPredicate(keywords);

        String expected = CommentContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
