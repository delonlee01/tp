package seedu.recruittrackpro.logic.commands;

import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.recruittrackpro.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());
        expectedModel = new ModelManager(model.getRecruitTrackPro(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
