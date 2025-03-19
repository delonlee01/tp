package seedu.recruittrackpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.testutil.PersonBuilder;

public class RemoveTagCommandTest {
    private Model model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(outOfBoundIndex, new Tag("test"));

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeTag_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).removeTags("friends").build();

        Tag tagToRemove = new Tag("friends");
        RemoveTagCommand command = new RemoveTagCommand(INDEX_FIRST_PERSON, tagToRemove);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAGS_SUCCESS,
                updatedPerson.getName(), tagToRemove);

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_tagNotInList_throwsCommandException() {
        Tag nonExistentTag = new Tag("nonexistentTag");
        RemoveTagCommand command = new RemoveTagCommand(INDEX_FIRST_PERSON, nonExistentTag);

        assertCommandFailure(command, model, RemoveTagCommand.MESSAGE_TAG_NOT_IN_LIST);
    }

    @Test
    public void execute_emptyPersonList_throwsCommandException() {
        Model emptyModel = new ModelManager(new RecruitTrackPro(), new UserPrefs()); // Empty model
        RemoveTagCommand command = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("test"));

        assertCommandFailure(command, emptyModel, RemoveTagCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void equals() {
        RemoveTagCommand removeTagCommand1 = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("test1"));
        RemoveTagCommand removeTagCommand2 = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("test2"));
        RemoveTagCommand removeTagCommand3 = new RemoveTagCommand(
                Index.fromOneBased(2), new Tag("test1"));

        // Same object -> returns true
        assertTrue(removeTagCommand1.equals(removeTagCommand1));

        // Same values -> returns true
        RemoveTagCommand removeTagCommandCopy = new RemoveTagCommand(INDEX_FIRST_PERSON, new Tag("test1"));
        assertTrue(removeTagCommand1.equals(removeTagCommandCopy));

        // Different tag -> returns false
        assertFalse(removeTagCommand1.equals(removeTagCommand2));

        // Different index -> returns false
        assertFalse(removeTagCommand1.equals(removeTagCommand3));

        // Null -> returns false
        assertFalse(removeTagCommand1.equals(null));

        // Different type -> returns false
        assertFalse(removeTagCommand1.equals(5));
    }

    @Test
    public void toStringTest() {
        Index index = Index.fromOneBased(1);
        Tag tag = new Tag("friends");
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, tag);

        String expectedString = "seedu.recruittrackpro.logic.commands.RemoveTagCommand"
                + "{targetIndex=" + index + ", tagToRemove=" + tag + "}";
        assertEquals(expectedString, removeTagCommand.toString());
    }
}
