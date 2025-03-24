package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.function.Predicate;

import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.person.Person;

/**
 * Finds and lists all persons in RecruitTrackPro whose names or attributes contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all candidates whose names or attributes contain any of "
            + "the specified keywords (case-insensitive).";

    public static final String MESSAGE_USAGE = SHORT_MESSAGE_USAGE
            + "\nParameters: "
            + PREFIX_NAME + " NAME [MORE_NAMES] or \n"
            + PREFIX_TAG + " TAG [MORE_TAGS] or \n"
            + PREFIX_ADDRESS + " ADDRESS [MORE_ADDRESSES] or \n"
            + PREFIX_EMAIL + " EMAIL [MORE_EMAILS] or \n"
            + PREFIX_PHONE + " PHONE [MORE_PHONES] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "alice bob charlie"
            + " " + PREFIX_EMAIL + "alice@gmail.com bob@yahoo.com charlie@hotmail.com";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
