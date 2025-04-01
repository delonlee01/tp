package seedu.recruittrackpro.logic.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        AddressContainsKeywordsPredicate predicate1 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address1"}), false);
        AddressContainsKeywordsPredicate predicate2 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address1"}), false);
        AddressContainsKeywordsPredicate predicate3 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address2"}), false);
        assertTrue(predicate1.equals(predicate1));
        assertTrue(predicate1.equals(predicate2));
        assertFalse(predicate1.equals(predicate3));
        assertFalse(predicate1.equals("not a predicate"));
        assertFalse(predicate1.equals(null));
    }

    @Test
    public void test_containAddressKeywords_returnsTrue() {
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"keyword1"}), false);
        assertTrue(predicate.test(new PersonBuilder().withAddress("keyword1").build()));

        AddressContainsKeywordsPredicate predicate2 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"keyword1", "keyword2"}), true);
        assertTrue(predicate2.test(new PersonBuilder().withAddress("keyword1 keyword2").build()));
    }

    @Test
    public void test_doesNotContainAddressKeywords_returnsFalse() {
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"keyword1"}), false);
        assertFalse(predicate.test(new PersonBuilder().withAddress("keyword2").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords, false);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
