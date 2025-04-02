package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruittrackpro.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruittrackpro.model.Model;

/**
 * Lists all persons in RecruitTrackPro to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + ": List all candidates.";

    public static final String MESSAGE_SUCCESS = "Listed all candidates";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
