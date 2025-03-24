package seedu.recruittrackpro.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.parser.ParserUtil.parseTag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.recruittrackpro.logic.parser.exceptions.ParseException;

/**
 * Represents a wrapper around a set of Tag objects, with utility methods.
 */
public class Tags {
    private final Set<Tag> tags;

    /**
     * Constructs a {@code Tags} instance from a collection of raw tag names.
     *
     * @param tagList The list of tag strings to parse and wrap.
     * @throws ParseException if any tag is invalid.
     */
    public Tags(Collection<String> tagList) throws ParseException {
        requireNonNull(tagList);
        this.tags = new HashSet<>();
        for (String tagName : tagList) {
            this.tags.add(parseTag(tagName));
        }
    }

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
    public Tags combineTags(Tags toAdd) {
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
    public Tags excludeTags(Tags toRemove) {
        requireNonNull(toRemove);
        Set<Tag> updated = new HashSet<>(this.tags);
        updated.removeAll(toRemove.tags);
        return new Tags(updated);
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
     * Splits the incoming tags into new and duplicate tags with respect to this instance.
     *
     * @param incoming Tags to be added.
     * @return A {@code TagSeparationResult} with new tags and duplicates.
     */
    public TagSeparationResult separateNewFromExisting(Tags incoming) {
        Set<Tag> newTags = new HashSet<>();
        Set<Tag> duplicateTags = new HashSet<>();

        for (Tag tag : incoming.tags) {
            if (this.tags.contains(tag)) {
                duplicateTags.add(tag);
            } else {
                newTags.add(tag);
            }
        }

        return new TagSeparationResult(new Tags(newTags), new Tags(duplicateTags));
    }

    /**
     * Returns {@code true} if this {@code Tags} instance contains no tags.
     *
     * @return {@code true} if the internal tag set is empty; {@code false} otherwise.
     */
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
        return tags.stream()
                .map(tag -> "\"" + tag.tagName + "\"")
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Represents the result of separating incoming tags into new and duplicate tags.
     *
     * @param newTags Tags that do not yet exist and can be added.
     * @param duplicateTags Tags that are already present in the existing {@code Tags} instance.
     */
    public record TagSeparationResult(Tags newTags, Tags duplicateTags) {
        @Override
        public String toString() {
            return "New: " + newTags + ", Duplicates: " + duplicateTags;
        }
    }
}
