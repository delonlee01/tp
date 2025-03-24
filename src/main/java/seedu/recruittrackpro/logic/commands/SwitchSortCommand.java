package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruittrackpro.model.Model;

/**
 * Switches sort between ascending and descending in the address book.
 */
public class SwitchSortCommand extends Command {

    public static final String COMMAND_WORD = "switch-sort";

    public static final String MESSAGE_SUCCESS = "List sorting has been switched to %s";

    private static final String MESSAGE_ASCENDING = "ascending";
    private static final String MESSAGE_DESCENDING = "descending";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.switchPersonListSorting();
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                model.isAscending() ? MESSAGE_ASCENDING : MESSAGE_DESCENDING));
    }
}
