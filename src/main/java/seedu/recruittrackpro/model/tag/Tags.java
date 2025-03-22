package seedu.recruittrackpro.model.tag;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.util.Pair;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;

/**
 * Represents a wrapper around a set of Tag objects, with utility methods.
 */
public class Tags {
    private final Set<Tag> tags;

    /**
     * Constructs a {@code Tags} instance from a set of {@link Tag}.
     *
     * @param tags The set of tags to wrap. Cannot be null.
     */
    public Tags(Set<Tag> tags) {
        requireNonNull(tags);
        this.tags = new HashSet<>(tags);
    }

    /**
     * Constructs an empty {@code Tags} instance.
     */
    public Tags() {
        this.tags = new HashSet<>();
    }

    /**
     * Returns a new {@code Tags} instance with the specified tag(s) added.
     *
     * @param toAdd Tag(s) to be added.
     * @return A new {@code Tags} instance.
     */
    public Tags addTags(Tags toAdd) {
        requireNonNull(toAdd);
        Set<Tag> updated = new HashSet<>(this.tags);
        updated.addAll(toAdd.tags);
        return new Tags(updated);
    }

    /**
     * Returns a new {@code Tags} instance with the specified tag(s) removed.
     *
     * @param toRemove Tag(s) to be removed.
     * @return A new {@code Tags} instance.
     */
    public Tags removeTags(Tags toRemove) {
        requireNonNull(toRemove);
        Set<Tag> updated = new HashSet<>(this.tags);
        updated.removeAll(toRemove.tags);
        return new Tags(updated);
    }

    /**
     * Parses a collection of tag name strings into a {@code Tags} object.
     *
     * @param tagList A collection of tag name strings.
     * @return A {@code Tags} object containing parsed {@link Tag} instances.
     * @throws ParseException if any tag is invalid.
     */
    public static Tags fromListToTags(Collection<String> tagList) throws ParseException {
        requireNonNull(tagList);
        Set<Tag> parsedTags = new HashSet<>();
        for (String tagName : tagList) {
            parsedTags.add(parseTag(tagName));
        }
        return new Tags(parsedTags);
    }

    /**
     * Parses a single {@code String} into a {@link Tag}.
     *
     * @param tag The raw tag name.
     * @return A valid {@link Tag} instance.
     * @throws ParseException if the tag format is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Returns an unmodifiable copy of the internal tag set.
     * Any attempt to modify the returned set will throw {@link UnsupportedOperationException}.
     */
    public Set<Tag> toSet() {
        return Collections.unmodifiableSet(new HashSet<>(tags));
    }

    /**
     * Checks if this {@code Tags} instance contains the specified {@link Tag}.
     * <p>
     * Comparison is case-insensitive, as defined by the {@link Tag#equals(Object)} implementation.
     *
     * @param tag The {@code Tag} to check for presence.
     * @return {@code true} if this tag exists in the internal set; {@code false} otherwise.
     */
    public boolean contains(Tag tag) {
        return tags.contains(tag);
    }

    /**
     * Checks for duplicates between current and incoming tags.
     *
     * @param incoming Tags to be added.
     * @return A {@code Pair}: (newUniqueTags, duplicateTags)
     */
    public Pair<Tags, Tags> checkDuplicates(Tags incoming) {
        Set<Tag> newTags = new HashSet<>();
        Set<Tag> dupes = new HashSet<>();

        for (Tag tag : incoming.tags) {
            if (this.tags.contains(tag)) {
                dupes.add(tag);
            } else {
                newTags.add(tag);
            }
        }
        return new Pair<>(new Tags(newTags), new Tags(dupes));
    }

    public boolean isEmpty() {
        return tags.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tags)) {
            return false;
        }
        Tags other = (Tags) o;
        return Objects.equals(tags, other.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }

    @Override
    public String toString() {
        return tags.toString();
    }
}
