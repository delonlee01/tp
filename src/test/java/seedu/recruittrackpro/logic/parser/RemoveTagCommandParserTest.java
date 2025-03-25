package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.logic.commands.RemoveTagCommand;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;

public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() {
        assertParseSuccess(parser, "1 t/friends",
                new RemoveTagCommand(INDEX_FIRST_PERSON, new Tags(Set.of(new Tag("friends")))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
