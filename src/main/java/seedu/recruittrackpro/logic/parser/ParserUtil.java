package seedu.recruittrackpro.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.person.Address;
import seedu.recruittrackpro.model.person.Comment;
import seedu.recruittrackpro.model.person.Email;
import seedu.recruittrackpro.model.person.Name;
import seedu.recruittrackpro.model.person.Phone;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String MESSAGE_DUPLICATE_TAGS =
            "Duplicate tag inputs detected: %s. Tags must be unique (i.e. case-insensitive).";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
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
     * Parses {@code Collection<String> tagList} into a {@code Tags}.
     */
    public static Tags parseTags(Collection<String> tagList) throws ParseException {
        requireNonNull(tagList);
        return new Tags(tagList);
    }

    /**
     * Throws a ParseException if there are duplicate tags in the given list (case-insensitive).
     *
     * @param tagValues list of tag input strings
     * @throws ParseException if duplicates are found
     */
    public static void throwIfDuplicateTags(List<String> tagValues) throws ParseException {
        List<String> duplicates = getDuplicateInputStrings(tagValues);
        if (!duplicates.isEmpty()) {
            String formatted = duplicates.stream()
                    .map(s -> "\"" + s + "\"")
                    .collect(Collectors.joining(", ", "[", "]"));
            throw new ParseException(String.format(MESSAGE_DUPLICATE_TAGS, formatted));
        }
    }

    /**
     * Parses a {@code String comment} into a {@code Comment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code comment} is invalid.
     */
    public static Comment parseComment(String comment) throws ParseException {
        requireNonNull(comment);
        String trimmedComment = comment.trim();
        if (!Comment.isValidComment(trimmedComment)) {
            throw new ParseException(Comment.MESSAGE_CONSTRAINTS);
        }
        return new Comment(trimmedComment);
    }

    private static List<String> getDuplicateInputStrings(List<String> values) {
        Map<String, List<String>> grouped = new HashMap<>();

        for (String val : values) {
            String trimmed = val.trim();
            String lower = trimmed.toLowerCase();
            grouped.computeIfAbsent(lower, k -> new ArrayList<>()).add(trimmed);
        }

        return grouped.values().stream()
                .filter(list -> list.size() > 1) // only duplicates
                .flatMap(List::stream) // flatten the list of lists
                .collect(Collectors.toList());
    }
}
