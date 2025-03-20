package seedu.recruittrackpro.logic.predicates;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class AddressContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        AddressContainsKeywordsPredicate predicate1 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address1"}));
        AddressContainsKeywordsPredicate predicate2 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address1"}));
        AddressContainsKeywordsPredicate predicate3 =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"address2"}));
        assertEquals(predicate1, predicate2);
        assertNotEquals(predicate1, predicate3);
    }

    @Test
    public void test_containAddressKeywords_returnsTrue() {
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"keyword1"}));
        assertTrue(predicate.test(new PersonBuilder().withAddress("keyword1").build()));
    }

    @Test
    public void test_doesNotContainAddressKeywords_returnsFalse() {
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(List.of(new String[]{"keyword1"}));
        assertFalse(predicate.test(new PersonBuilder().withAddress("keyword2").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(keywords);

        String expected = AddressContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
