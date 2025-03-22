package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.commands.AddTagsCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Parses input arguments and creates a new AddTagsCommand object
 */
public class AddTagsCommandParser implements Parser<AddTagsCommand> {
    @Override
    public AddTagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        String preamble = argMultimap.getPreamble().trim();
        List<String> tagValues = argMultimap.getAllValues(PREFIX_TAG);

        // Ensure the command has both an index and at least one valid t/ prefix
        if (preamble.isEmpty() || !preamble.matches("\\d+") || tagValues.isEmpty()) {
            throw new ParseException(AddTagsCommand.MESSAGE_USAGE);
        }

        // Ensure all `t/` prefixes have values (no empty tags)
        if (tagValues.stream().anyMatch(String::isBlank)) {
            throw new ParseException("Tag values cannot be empty.");
        }

        // Verify that only allowed prefixes are used
        argMultimap.verifyOnlyHavePrefixesFor(PREFIX_TAG);

        // Process valid tags
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Tags tags = Tags.fromListToTags(tagValues);

        if (tags.isEmpty()) {
            throw new ParseException(AddTagsCommand.MESSAGE_NO_TAGS_FOUND);
        }

        return new AddTagsCommand(index, tags);
    }
}
