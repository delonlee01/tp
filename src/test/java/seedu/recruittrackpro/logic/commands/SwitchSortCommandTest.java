package seedu.recruittrackpro.logic.commands;

import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SwitchSortCommand.
 */
public class SwitchSortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());
        expectedModel = new ModelManager(model.getRecruitTrackPro(), new UserPrefs());
    }

    @Test
    public void execute_switchSortingSuccess() {
        expectedModel.switchRecruitTrackProSorting();

        assertCommandSuccess(new SwitchSortCommand(), model,
                SwitchSortCommand.MESSAGE_SUCCESS_DESCENDING, expectedModel);

        expectedModel.switchRecruitTrackProSorting();

        assertCommandSuccess(new SwitchSortCommand(), model,
                SwitchSortCommand.MESSAGE_SUCCESS_ASCENDING, expectedModel);
    }

}
