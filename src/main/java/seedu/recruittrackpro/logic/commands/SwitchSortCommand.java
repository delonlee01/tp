package seedu.recruittrackpro.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruittrackpro.model.Model;

/**
 * Switches sort between ascending and descending in the address book.
 */
public class SwitchSortCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_SUCCESS = "List sorting has been switched";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.switchPersonListSorting();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
