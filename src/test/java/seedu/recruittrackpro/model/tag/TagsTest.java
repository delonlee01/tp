package seedu.recruittrackpro.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.recruittrackpro.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;

public class TagsTest {

    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "colleague";
    private static final String INVALID_TAG = " ";
    private static final String WHITESPACE = " \t\n";

    // ========== Tag Collection Behavior Tests ==========

    @Test
    public void addTags_tagsNotPresent_addsSuccessfully() {
        Tags base = new Tags(Set.of(new Tag(VALID_TAG_1)));
        Tags toAdd = new Tags(Set.of(new Tag(VALID_TAG_2)));

        Tags result = base.addTags(toAdd);
        Set<Tag> expected = Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2));

        assertEquals(expected, result.toSet());
    }

    @Test
    public void removeTags_tagsPresent_removesSuccessfully() {
        Tag tag1 = new Tag(VALID_TAG_1);
        Tag tag2 = new Tag(VALID_TAG_2);
        Tags base = new Tags(Set.of(tag1, tag2));
        Tags toRemove = new Tags(Set.of(tag1));

        Tags result = base.removeTags(toRemove);
        assertEquals(Set.of(tag2), result.toSet());
    }

    @Test
    public void toSet_modifyingReturnedSet_doesNotAffectOriginal() {
        Tag tag = new Tag(VALID_TAG_1);
        Tags tags = new Tags(Set.of(tag));

        Set<Tag> returnedSet = tags.toSet();
        Set<Tag> modifiedCopy = new HashSet<>(returnedSet);
        modifiedCopy.remove(tag); // safe, not touching the internal set

        // Confirm the internal set in Tags remains unchanged
        assertEquals(Set.of(tag), tags.toSet());
    }

    @Test
    public void equalsAndHashCode_sameContent_returnsTrue() {
        Tags tags1 = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        Tags tags2 = new Tags(Set.of(new Tag(VALID_TAG_2), new Tag(VALID_TAG_1)));

        assertEquals(tags1, tags2);
        assertEquals(tags1.hashCode(), tags2.hashCode());
    }

    @Test
    public void equals_differentContent_returnsFalse() {
        Tags tags1 = new Tags(Set.of(new Tag(VALID_TAG_1)));
        Tags tags2 = new Tags(Set.of(new Tag(VALID_TAG_2)));

        assertNotEquals(tags1, tags2);
    }

    // ========== Tag Parsing Tests ==========

    @Test
    public void parseTag_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tags.parseTag(null));
    }

    @Test
    public void parseTag_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> Tags.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validInputWithoutWhitespace_returnsTag() throws Exception {
        Tag expected = new Tag(VALID_TAG_1);
        assertEquals(expected, Tags.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validInputWithWhitespace_returnsTrimmedTag() throws Exception {
        Tag expected = new Tag(VALID_TAG_1);
        String padded = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        assertEquals(expected, Tags.parseTag(padded));
    }

    // ========== List to Tags Conversion Tests ==========

    @Test
    public void fromListToTags_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tags.fromListToTags(null));
    }

    @Test
    public void fromListToTags_containsInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> Tags.fromListToTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void fromListToTags_emptyList_returnsEmptyTags() throws Exception {
        Tags tags = Tags.fromListToTags(Collections.emptyList());
        assertEquals(Collections.emptySet(), tags.toSet());
    }

    @Test
    public void fromListToTags_allValidTags_returnsTagsInstance() throws Exception {
        Tags tags = Tags.fromListToTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expected = Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2));
        assertEquals(expected, tags.toSet());
    }

    // ========== Duplicate Check Tests ==========

    @Test
    public void checkDuplicates_mixedInput_returnsCorrectSeparation() {
        Tags existing = new Tags(Set.of(new Tag(VALID_TAG_1)));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        Pair<Tags, Tags> result = existing.checkDuplicates(incoming);
        Tags expectedUnique = new Tags(Set.of(new Tag(VALID_TAG_2)));
        Tags expectedDuplicates = new Tags(Set.of(new Tag(VALID_TAG_1)));

        assertEquals(expectedUnique, result.getKey());
        assertEquals(expectedDuplicates, result.getValue());
    }

    @Test
    public void checkDuplicates_allDuplicates_returnsEmptyUnique() {
        Tags existing = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_2)));

        Pair<Tags, Tags> result = existing.checkDuplicates(incoming);
        assertEquals(new Tags(), result.getKey()); // No new tags
        assertEquals(new Tags(Set.of(new Tag(VALID_TAG_2))), result.getValue());
    }

    @Test
    public void checkDuplicates_allUnique_returnsAllInUnique() {
        Tags existing = new Tags(Set.of(new Tag("existing")));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        Pair<Tags, Tags> result = existing.checkDuplicates(incoming);
        assertEquals(new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2))), result.getKey());
        assertEquals(new Tags(), result.getValue()); // No duplicates
    }
}
