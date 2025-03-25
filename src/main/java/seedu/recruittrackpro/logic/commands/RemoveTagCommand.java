package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recruittrackpro.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.commons.util.ToStringBuilder;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.logic.commands.exceptions.CommandException;
import seedu.recruittrackpro.logic.descriptors.EditPersonDescriptor;
import seedu.recruittrackpro.logic.util.EditPersonUtil;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;

/**
 * Removes a specified tag from an existing candidate in the address book.
 */
public class RemoveTagCommand extends Command {
    public static final String COMMAND_WORD = "remove-tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a specified tag from a candidate "
            + "using the index number from the displayed person list. "
            + "The specified tag will be removed from the person's existing tag list if it matches exactly, "
            + "ignoring case sensitivity.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "Java Developer";
    public static final String MESSAGE_REMOVE_TAGS_SUCCESS = "Tag removed from %1$s: %2$s";
    public static final String MESSAGE_TAG_NOT_IN_LIST = "Tag specified is not in the persons tag list.";
    public static final String MESSAGE_EMPTY_LIST = "No candidates to edit! The displayed list is empty.";

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
     * Returns a new {@code Person} object with the specified tags removed.
     *
     * <p>This method excludes all tags in {@code tagsToRemove} from the target person's
     * current tag set, and constructs an {@code EditPersonDescriptor} with the updated tags.
     * It then uses {@code EditPersonUtil} to generate and return the edited person.
     *
     * @param targetPerson The person whose tags are to be removed.
     * @param tagsToRemove The set of tags to be removed from the person.
     * @return A new {@code Person} object with the specified tags removed.
     */
    Person removeTags(Person targetPerson, Tags tagsToRemove) {
        Tags personTags = targetPerson.getTags();
        EditPersonDescriptor editedDescriptor = new EditPersonDescriptor();
        editedDescriptor.setTags(personTags.excludeTags(tagsToRemove));
        return EditPersonUtil.createEditedPerson(targetPerson, editedDescriptor);
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

        if (!targetPerson.getTags().contains(tagToRemove)) {
            throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
        }

        Person updatedPerson = removeTags(targetPerson, new Tags(Set.of(tagToRemove)));
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
