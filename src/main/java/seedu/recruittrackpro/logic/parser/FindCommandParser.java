package seedu.recruittrackpro.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import seedu.recruittrackpro.logic.commands.FindCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.person.Name;
import seedu.recruittrackpro.model.person.NameContainsKeywordsPredicate;
import seedu.recruittrackpro.model.person.TagContainsKeywordsPredicate;
import seedu.recruittrackpro.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!hasAtLeastOnePrefixPresent(argMultimap, PREFIX_NAME, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = getNameKeywords(argMultimap);
        String[] tagKeywords = getTagKeywords(argMultimap);

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));

        return new FindCommand(namePredicate.or(tagPredicate));
    }

    /**
     * Returns true if at least one of the prefixes does not contain empty {@code Optional} values.
     */
    public boolean hasAtLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix ... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private boolean containsPrefix(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

    private String[] getNameKeywords(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!containsPrefix(argumentMultimap, PREFIX_NAME)) {
            return new String[0];
        }
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
        String name = argumentMultimap.getValue(PREFIX_NAME).orElse("");

        if (name.isEmpty()) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        return name.split("\\s+");
    }

    private String[] getTagKeywords(ArgumentMultimap argumentMultimap) throws ParseException {
        if (!containsPrefix(argumentMultimap, PREFIX_TAG)) {
            return new String[0];
        }
        Set<Tag> tagList = ParserUtil.parseTags(argumentMultimap.getAllValues(PREFIX_TAG));
        return tagList.stream().map(tag -> tag.tagName).toArray(String[]::new);
    }

}
