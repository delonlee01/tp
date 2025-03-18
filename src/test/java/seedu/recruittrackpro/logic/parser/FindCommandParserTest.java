package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recruittrackpro.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.logic.commands.FindCommand;
import seedu.recruittrackpro.logic.predicates.ContainsKeywordPredicate;
import seedu.recruittrackpro.model.person.Name;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // missing arguments
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // blank argument
        assertParseFailure(parser, " " + PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " " + PREFIX_NAME + "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        Object[] nameKeywords = {PREFIX_NAME, new String[]{"Alice", "Bob"}};
        FindCommand expectedFindCommand = new FindCommand(new ContainsKeywordPredicate(nameKeywords));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
