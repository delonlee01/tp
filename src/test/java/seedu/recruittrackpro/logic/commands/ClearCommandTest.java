package seedu.recruittrackpro.logic.commands;

import static seedu.recruittrackpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.recruittrackpro.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import org.junit.jupiter.api.Test;

import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyRecruitTrackPro_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecruitTrackPro_success() {
        Model model = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalRecruitTrackPro(), new UserPrefs());
        expectedModel.setRecruitTrackPro(new RecruitTrackPro());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
