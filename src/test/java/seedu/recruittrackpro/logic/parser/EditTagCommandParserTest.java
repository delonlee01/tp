package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.logic.commands.EditTagCommand;
import seedu.recruittrackpro.model.tag.Tag;

public class EditTagCommandParserTest {
    private final EditTagCommandParser parser = new EditTagCommandParser();

    @Test
    public void parse_validArgs_returnsEditTagCommand() {
        String input = "1 from/friends to/colleague";
        EditTagCommand expected = new EditTagCommand(INDEX_FIRST_PERSON,
                new Tag("friends"), new Tag("colleague"));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_missingIndex_failure() {
        String input = "from/friends to/colleague";
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingFromOrTo_failure() {
        assertParseFailure(parser, "1 to/colleague",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 from/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a from/friends to/colleague",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
    }
}
