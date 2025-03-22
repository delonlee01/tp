package seedu.recruittrackpro.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.commands.AddTagsCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Contains unit tests for {@code AddTagsCommandParser}.
 */
public class AddTagsCommandParserTest {
    private final AddTagsCommandParser parser = new AddTagsCommandParser();

    @Test
    public void parse_validInputSingleTag_success() throws Exception {
        String userInput = "1 t/Java";
        AddTagsCommand expectedCommand = new AddTagsCommand(
                Index.fromOneBased(1),
                new Tags(Set.of(new Tag("Java")))
        );

        AddTagsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_validInputMultipleTags_success() throws Exception {
        String userInput = "1 t/Java t/Python t/C++";
        AddTagsCommand expectedCommand = new AddTagsCommand(
                Index.fromOneBased(1),
                new Tags(Set.of(new Tag("Java"), new Tag("Python"), new Tag("C++")))
        );

        AddTagsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String userInput = "t/Java";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_failure() {
        String userInput = "a t/Java"; // 'a' is not a valid index
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noTagsProvided_failure() {
        String userInput = "1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyTag_failure() {
        String userInput = "1 t/";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidPrefix_failure() {
        String userInput = "1 t/Java n/James";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_mixedValidAndInvalidPrefix_failure() {
        String userInput = "1 t/Java p/12345678";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_trailingWhitespace_success() throws Exception {
        String userInput = "1 t/Java  ";
        AddTagsCommand expectedCommand = new AddTagsCommand(
                Index.fromOneBased(1),
                new Tags(Set.of(new Tag("Java")))
        );

        AddTagsCommand actualCommand = parser.parse(userInput);
        assertEquals(expectedCommand, actualCommand);
    }
}
