package seedu.recruittrackpro.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.recruittrackpro.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tags.TagSeparationResult;

public class TagsTest {

    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "colleague";
    private static final String INVALID_TAG = " ";
    private static final String WHITESPACE = " \t\n";

    // ========== Constructors ==========

    @Test
    public void constructor_collectionStringNullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tags((List<String>) null));
    }

    @Test
    public void constructor_collectionStringContainsInvalid_throwsParseException() {
        List<String> input = Arrays.asList(VALID_TAG_1, INVALID_TAG);
        assertThrows(ParseException.class, () -> new Tags(input));
    }

    @Test
    public void constructor_collectionStringValidTags_success() throws ParseException {
        Tags tags = new Tags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Tags expected = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        assertEquals(expected, tags);
    }

    @Test
    public void constructor_empty_success() {
        Tags tags = new Tags();
        assertEquals(new Tags(), tags);
    }

    @Test
    public void constructor_setOfTags_success() {
        Set<Tag> tagSet = Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2));
        Tags tags = new Tags(tagSet);
        Tags expected = new Tags(tagSet);
        assertEquals(expected, tags);
    }

    // ========== Tag Collection Behavior Tests ==========

    @Test
    public void combineTags_tagsNotPresent_addsSuccessfully() {
        Tags base = new Tags(Set.of(new Tag(VALID_TAG_1)));
        Tags toAdd = new Tags(Set.of(new Tag(VALID_TAG_2)));

        Tags result = base.combineTags(toAdd);
        Tags expected = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expected, result);
    }

    @Test
    public void excludeTags_tagsPresent_removesSuccessfully() {
        Tag tag1 = new Tag(VALID_TAG_1);
        Tag tag2 = new Tag(VALID_TAG_2);
        Tags base = new Tags(Set.of(tag1, tag2));
        Tags toRemove = new Tags(Set.of(tag1));

        Tags result = base.excludeTags(toRemove);
        Tags expected = new Tags(Set.of(tag2));
        assertEquals(expected, result);
    }

    @Test
    public void stream_doesNotAllowModifyingOriginalSet() {
        Tag tag = new Tag(VALID_TAG_1);
        Tags tags = new Tags(Set.of(tag));

        Set<Tag> streamCopy = tags.toStream().collect(java.util.stream.Collectors.toSet());
        streamCopy.remove(tag); // safe: this does not affect original tags

        assertEquals(new Tags(Set.of(tag)), tags); // still equals original
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

    // ========== TagSeparationResult Tests ==========

    @Test
    public void separateNewFromExisting_mixedInput_returnsCorrectSeparation() {
        Tags existing = new Tags(Set.of(new Tag(VALID_TAG_1)));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        TagSeparationResult result = existing.separateNewFromExisting(incoming);
        Tags expectedUnique = new Tags(Set.of(new Tag(VALID_TAG_2)));
        Tags expectedDuplicates = new Tags(Set.of(new Tag(VALID_TAG_1)));

        assertEquals(expectedUnique, result.newTags());
        assertEquals(expectedDuplicates, result.duplicateTags());
    }

    @Test
    public void separateNewFromExisting_allDuplicates_returnsEmptyUnique() {
        Tags existing = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_2)));

        TagSeparationResult result = existing.separateNewFromExisting(incoming);
        assertEquals(new Tags(), result.newTags());
        assertEquals(new Tags(Set.of(new Tag(VALID_TAG_2))), result.duplicateTags());
    }

    @Test
    public void separateNewFromExisting_allUnique_returnsAllInUnique() {
        Tags existing = new Tags(Set.of(new Tag("existing")));
        Tags incoming = new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        TagSeparationResult result = existing.separateNewFromExisting(incoming);
        assertEquals(new Tags(Set.of(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2))), result.newTags());
        assertEquals(new Tags(), result.duplicateTags());
    }
}
