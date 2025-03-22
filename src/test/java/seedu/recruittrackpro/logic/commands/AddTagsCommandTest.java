package seedu.recruittrackpro.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.commons.core.index.Index;
import seedu.recruittrackpro.logic.Messages;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.tag.Tags;
import seedu.recruittrackpro.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagsCommand.
 */
public class AddTagsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());
    }

    @Test
    public void execute_addNewTags_success() throws ParseException {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tags newTags = Tags.fromListToTags(List.of("Java", "Spring"));

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, newTags);

        Person updatedPerson = new PersonBuilder(personToEdit).addTags("Java", "Spring").build();

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                updatedPerson.getName(), newTags.toString());

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateTags_onlyShowDuplicates() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Tags duplicateTags = personToEdit.getTags();

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, duplicateTags);
        String expectedMessage = String.format(
                AddTagsCommand.MESSAGE_DUPLICATE_TAGS,
                personToEdit.getName(),
                duplicateTags.toString()
        );

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewAndDuplicateTags_mixedResponse() throws ParseException {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Tags existingTags = personToEdit.getTags();
        Tags newTags = Tags.fromListToTags(List.of("Python"));
        Tags combinedTags = existingTags.addTags(newTags);

        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, combinedTags);

        Person updatedPerson = new PersonBuilder(personToEdit).addTags("Python").build();

        String expectedMessage = String.format(AddTagsCommand.MESSAGE_ADD_TAGS_SUCCESS,
                updatedPerson.getName(), newTags.toString())
                + "\n" + String.format(AddTagsCommand.MESSAGE_DUPLICATE_TAGS,
                updatedPerson.getName(), existingTags.toString());

        Model expectedModel = new ModelManager(new RecruitTrackPro(model.getRecruitTrackPro()), new UserPrefs());
        expectedModel.setPerson(personToEdit, updatedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws ParseException {
        Tags tags = Tags.fromListToTags(List.of("Java"));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTagsCommand command = new AddTagsCommand(outOfBoundIndex, tags);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTagsProvided_throwsCommandException() {
        Tags emptyTags = new Tags();
        AddTagsCommand command = new AddTagsCommand(INDEX_FIRST_PERSON, emptyTags);

        assertCommandFailure(command, model, AddTagsCommand.MESSAGE_NO_TAGS_FOUND);
    }

    @Test
    public void equals() throws ParseException {
        Tags tagsA = Tags.fromListToTags(List.of("Java"));
        Tags tagsB = Tags.fromListToTags(List.of("Python"));

        AddTagsCommand commandA = new AddTagsCommand(INDEX_FIRST_PERSON, tagsA);
        AddTagsCommand commandB = new AddTagsCommand(INDEX_FIRST_PERSON, tagsB);
        AddTagsCommand commandC = new AddTagsCommand(INDEX_SECOND_PERSON, tagsA);

        // same values -> returns true
        AddTagsCommand commandWithSameValues = new AddTagsCommand(INDEX_FIRST_PERSON, new Tags(tagsA.toSet()));
        assertTrue(commandA.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(commandA.equals(commandA));

        // null -> returns false
        assertFalse(commandA.equals(null));

        // different types -> returns false
        assertFalse(commandA.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(commandA.equals(commandC));

        // different tags -> returns false
        assertFalse(commandA.equals(commandB));
    }

    @Test
    public void toStringTest() throws ParseException {
        Tags tags = Tags.fromListToTags(List.of("Java"));
        Index index = Index.fromOneBased(1);

        AddTagsCommand command = new AddTagsCommand(index, tags);
        String expected = AddTagsCommand.class.getCanonicalName()
                + "{index=" + index + ", tagsToAdd=" + tags + "}";
        assertEquals(expected, command.toString());
    }
}
