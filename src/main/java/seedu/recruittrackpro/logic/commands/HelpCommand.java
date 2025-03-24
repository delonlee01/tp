package seedu.recruittrackpro.logic.commands;

import seedu.recruittrackpro.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + ": Shows all available commands.";

    public static final String MESSAGE_USAGE = SHORT_MESSAGE_USAGE + "\nExample: " + COMMAND_WORD;

    public static final String ALL_HELP_MESSAGE = ListCommand.SHORT_MESSAGE_USAGE + "\n"
            + AddCommand.SHORT_MESSAGE_USAGE + "\n"
            + EditCommand.SHORT_MESSAGE_USAGE + "\n"
            + DeleteCommand.SHORT_MESSAGE_USAGE + "\n"
            + FindCommand.SHORT_MESSAGE_USAGE + "\n"
            + AddTagsCommand.SHORT_MESSAGE_USAGE + "\n"
            + RemoveTagCommand.SHORT_MESSAGE_USAGE + "\n"
            + HelpCommand.SHORT_MESSAGE_USAGE + "\n"
            + ClearCommand.SHORT_MESSAGE_USAGE + "\n"
            + ExitCommand.SHORT_MESSAGE_USAGE;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(ALL_HELP_MESSAGE);
    }
}
