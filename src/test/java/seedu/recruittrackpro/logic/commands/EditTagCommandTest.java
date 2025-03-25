package seedu.recruittrackpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tag;
import seedu.recruittrackpro.testutil.PersonBuilder;

public class EditTagCommandTest {
    private Model model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());

    @Test
    public void execute_validTagEdit_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person updatedPerson = new PersonBuilder(targetPerson)
                .removeTags("friends").addTags("besties").build();

        EditTagCommand command = new EditTagCommand(INDEX_FIRST_PERSON, new Tag("friends"), new Tag("besties"));

        String expectedMessage = String.format(EditTagCommand.MESSAGE_EDIT_TAGS_SUCCESS,
                targetPerson.getName(), new Tag("friends"), new Tag("besties"));

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());
        expectedModel.setPerson(targetPerson, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNotFound_throwsCommandException() {
        EditTagCommand command = new EditTagCommand(INDEX_FIRST_PERSON, new Tag("nonexistent"), new Tag("newtag"));
        assertCommandFailure(command, model, EditTagCommand.MESSAGE_TAG_NOT_IN_LIST);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Model emptyModel = new ModelManager(new RecruitTrackPro(), new UserPrefs());
        EditTagCommand command = new EditTagCommand(INDEX_FIRST_PERSON, new Tag("friends"), new Tag("newtag"));

        assertCommandFailure(command, emptyModel, EditTagCommand.MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditTagCommand command = new EditTagCommand(outOfBounds,
                new Tag("friends"), new Tag("newtag"));

        assertCommandFailure(command, model,
                seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditTagCommand cmd1 = new EditTagCommand(Index.fromOneBased(1), new Tag("a"), new Tag("b"));
        EditTagCommand cmd2 = new EditTagCommand(Index.fromOneBased(1), new Tag("a"), new Tag("b"));
        EditTagCommand cmd3 = new EditTagCommand(Index.fromOneBased(2), new Tag("a"), new Tag("b"));
        EditTagCommand cmd4 = new EditTagCommand(Index.fromOneBased(1), new Tag("a"), new Tag("c"));

        assertTrue(cmd1.equals(cmd1)); // same object
        assertTrue(cmd1.equals(cmd2)); // same values
        assertFalse(cmd1.equals(cmd3)); // different index
        assertFalse(cmd1.equals(cmd4)); // different new tag
        assertFalse(cmd1.equals(null)); // null
        assertFalse(cmd1.equals(5)); // different type
    }
}
