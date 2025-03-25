package seedu.recruittrackpro.logic.commands;

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

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.recruittrackpro.logic.parser.CliSyntax.PREFIX_TO;


/**
 * Removes a specified tag from an existing candidate in the address book.
 */
public class EditTagCommand extends Command {
    public static final String COMMAND_WORD = "edit-tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a specified tag of a candidate "
            + "using the index number from the displayed person list. "
            + "The specified tag will only be edited if it matches exactly, "
            + "ignoring case sensitivity.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_FROM + "TAG] [" + PREFIX_TO + "TAG]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FROM + "Java Developer " + PREFIX_TO + "Python Developer";
    public static final String MESSAGE_EDIT_TAGS_SUCCESS = "%1$s's tag edited from %2$s to %3$s";
    public static final String MESSAGE_TAG_NOT_IN_LIST = "Tag specified is not in the persons tag list.";
    public static final String MESSAGE_EMPTY_LIST = "No candidates to edit! The displayed list is empty.";

    private final Index targetIndex;
    private final Tag tagToEdit;
    private final Tag newTag;

    /**
     * Creates an EditTagCommand to edit a specified {@code tag} of the person at {@code index}.
     */
    public EditTagCommand(Index targetIndex, Tag tagToEdit, Tag newTag) {
        requireNonNull(targetIndex);
        requireNonNull(tagToEdit);
        this.targetIndex = targetIndex;
        this.tagToEdit = tagToEdit;
        this.newTag = newTag;
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

        Tags currentTags = targetPerson.getTags();
        if (!currentTags.contains(tagToEdit)) {
            throw new CommandException(MESSAGE_TAG_NOT_IN_LIST);
        }

        Tags newTags = currentTags.excludeTags(new Tags(Set.of(tagToEdit))).combineTags(new Tags(Set.of(newTag)));
        EditPersonDescriptor editedDescriptor = new EditPersonDescriptor();
        editedDescriptor.setTags(newTags);
        Person updatedPerson = EditPersonUtil.createEditedPerson(targetPerson, editedDescriptor);
        model.setPerson(targetPerson, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_TAGS_SUCCESS, targetPerson.getName(), tagToEdit, newTag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditTagCommand)) {
            return false;
        }

        EditTagCommand otherEditTagCommand = (EditTagCommand) other;
        return targetIndex.equals(otherEditTagCommand.targetIndex)
                && tagToEdit.equals(otherEditTagCommand.tagToEdit)
                && newTag.equals(otherEditTagCommand.newTag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("tagToEdit", tagToEdit)
                .add("newTag", newTag)
                .toString();
    }
}
