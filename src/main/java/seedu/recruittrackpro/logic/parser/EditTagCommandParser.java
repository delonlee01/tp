package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TO;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.commands.EditTagCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditTagCommand object.
 */
public class EditTagCommandParser implements Parser<EditTagCommand> {
    @Override
    public EditTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_FROM, PREFIX_TO);
        if (argMultimap.getValue(PREFIX_FROM).isEmpty() || argMultimap.getValue(PREFIX_TO).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE));
        }

        try {
            String preamble = argMultimap.getPreamble().trim();
            Index index = ParserUtil.parseIndex(preamble);
            String tagName = argMultimap.getValue(PREFIX_FROM).orElseThrow(() ->
                    new ParseException(Tag.MESSAGE_CONSTRAINTS));
            String newTag = argMultimap.getValue(PREFIX_TO).orElseThrow(() ->
                    new ParseException(Tag.MESSAGE_CONSTRAINTS));
            return new EditTagCommand(index, new Tag(tagName), new Tag(newTag));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE), pe);
        }
    }
}
