package seedu.recruittrackpro.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.commands.RemoveTagsCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Parses input arguments and creates a new RemoveTagsCommand object.
 */
public class RemoveTagsCommandParser implements Parser<RemoveTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagsCommand
     * and returns a RemoveTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);


        String preamble = argMultimap.getPreamble().trim();
        List<String> tagValues = argMultimap.getAllValues(PREFIX_TAG);

        if (preamble.isEmpty() || !preamble.matches("\\d+") || tagValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagsCommand.MESSAGE_USAGE));
        }

        if (tagValues.stream().anyMatch(String::isBlank)) {
            throw new ParseException("Tag values cannot be empty.");
        }

        argMultimap.verifyOnlyHavePrefixesFor(PREFIX_TAG);


        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Tags tags = new Tags(tagValues);

        if (tags.isEmpty()) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new RemoveTagsCommand(index, tags);
    }
}
