package seedu.recruittrackpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.model.tag.Tags;
import seedu.recruittrackpro.testutil.PersonBuilder;

public class RemoveTagsCommandTest {
    private Model model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws ParseException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveTagsCommand removeTagsCommand = new RemoveTagsCommand(outOfBoundIndex,
                new Tags(Collections.singleton("test")));

        assertCommandFailure(removeTagsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeTag_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).removeTags("friends").build();

        Tags tagsToRemove = new Tags(Set.of(new Tag("friends")));
        RemoveTagsCommand command = new RemoveTagsCommand(INDEX_FIRST_PERSON, tagsToRemove);

        String expectedMessage = String.format(RemoveTagsCommand.MESSAGE_REMOVE_TAGS_SUCCESS,
                updatedPerson.getName(), tagsToRemove);

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyPersonList_throwsCommandException() {
        Model emptyModel = new ModelManager(new RecruitTrackPro(), new UserPrefs()); // Empty model
        RemoveTagsCommand command = new RemoveTagsCommand(INDEX_FIRST_PERSON,
                new Tags(Set.of(new Tag("test"))));

        assertCommandFailure(command, emptyModel, RemoveTagsCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void equals() {
        RemoveTagsCommand removeTagsCommand1 = new RemoveTagsCommand(INDEX_FIRST_PERSON,
                new Tags(Set.of(new Tag("test1"))));
        RemoveTagsCommand removeTagsCommand2 = new RemoveTagsCommand(INDEX_FIRST_PERSON,
                new Tags(Set.of(new Tag("test2"))));
        RemoveTagsCommand removeTagsCommand3 = new RemoveTagsCommand(
                Index.fromOneBased(2),
                new Tags(Set.of(new Tag("test1"))));

        // Same object -> returns true
        assertTrue(removeTagsCommand1.equals(removeTagsCommand1));

        // Same values -> returns true
        RemoveTagsCommand removeTagsCommandCopy = new RemoveTagsCommand(INDEX_FIRST_PERSON,
                new Tags(Set.of(new Tag("test1"))));
        assertTrue(removeTagsCommand1.equals(removeTagsCommandCopy));

        // Different tag -> returns false
        assertFalse(removeTagsCommand1.equals(removeTagsCommand2));

        // Different index -> returns false
        assertFalse(removeTagsCommand1.equals(removeTagsCommand3));

        // Null -> returns false
        assertFalse(removeTagsCommand1.equals(null));

        // Different type -> returns false
        assertFalse(removeTagsCommand1.equals(5));
    }

    @Test
    public void toStringTest() {
        Index index = Index.fromOneBased(1);
        Tag tag = new Tag("friends");
        RemoveTagsCommand removeTagsCommand = new RemoveTagsCommand(index, new Tags(Set.of(tag)));

        String expectedString = "seedu.recruittrackpro.logic.commands.RemoveTagsCommand"
                + "{targetIndex=" + index + ", tagToRemove=[" + tag + "]}";
        assertEquals(expectedString, removeTagsCommand.toString());
    }
}
