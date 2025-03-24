package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruittrackpro.model.Model;

/**
 * Switches sort between ascending and descending in the address book.
 */
public class SwitchSortCommand extends Command {

    public static final String COMMAND_WORD = "switch-sort";

    public static final String MESSAGE_SUCCESS_ASCENDING =
            "List sorting has been switched to ascending";
    public static final String MESSAGE_SUCCESS_DESCENDING =
            "List sorting has been switched to descending";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.switchPersonListSorting();
        return new CommandResult(
                model.isAscending() ? MESSAGE_SUCCESS_ASCENDING : MESSAGE_SUCCESS_DESCENDING);
    }
}
