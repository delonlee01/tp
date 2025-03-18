package seedu.recruittrackpro.logic.commands;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.logic.commands.exceptions.CommandException;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.commands.DeleteCommand.MESSAGE_EMPTY_LIST;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recruittrackpro.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Removes a specified tag from an existing candidate in the address book.
 */
public class RemoveTagCommand extends Command {
    public static final String COMMAND_WORD = "remove-tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a specified tag from a candidate"
            + " using the index number from the displayed person list. "
            + "Specified tag will be removed from the person's existing tag list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Java Developer";
    public static final String MESSAGE_REMOVE_TAGS_SUCCESS = "Tag removed from %1$s: %2$s";
    public static final String MESSAGE_TAG_NOT_IN_LIST = "Tag specified is not in the persons tag list.";

    private final Index targetIndex;
    private final Tag tagToRemove;

    /**
     * Creates a RemoveTagCommand to remove a specified {@code tag} to the person at {@code index}.
     */
    public RemoveTagCommand(Index targetIndex, Tag tagToRemove) {
        requireNonNull(targetIndex);
        requireNonNull(tagToRemove);
        this.targetIndex = targetIndex;
        this.tagToRemove = tagToRemove;
    }

    /**
     * Creates a new updated person with the added tags.
     *
     * @param targetPerson The original person.
     * @param tagToRemove The tag to be removed.
     * @return A new Person object with updated tags.
     */
    private Person createUpdatedPerson(Person targetPerson, Tag tagToRemove) throws CommandException {
        Set<Tag> updatedTags = targetPerson.getTags();
        if (!updatedTags.remove(tagToRemove)) {
            throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
        }
        return new Person(targetPerson.getName(), targetPerson.getPhone(), targetPerson.getEmail(),
                targetPerson.getAddress(), updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        Person updatedPerson = createUpdatedPerson(targetPerson, tagToRemove);
        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_REMOVE_TAGS_SUCCESS, targetPerson.getName(), tagToRemove));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        RemoveTagCommand otherRemoveTagCommand = (RemoveTagCommand) other;
        return targetIndex.equals(otherRemoveTagCommand.targetIndex)
                && tagToRemove.equals(otherRemoveTagCommand.tagToRemove);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tagToRemove", tagToRemove)
                .toString();
    }
}
