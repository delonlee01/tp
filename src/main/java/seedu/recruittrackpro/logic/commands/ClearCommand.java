package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.RecruitTrackPro;

/**
 * Clears RecruitTrackPro.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + ": Deletes all candidates.";

    public static final String MESSAGE_SUCCESS = "RecruitTrackPro has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setRecruitTrackPro(new RecruitTrackPro());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
