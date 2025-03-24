package seedu.recruittrackpro.logic.commands;

import java.util.List;
import java.util.stream.Collectors;

import seedu.recruittrackpro.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD + ": Shows all available commands.";

    public static final String MESSAGE_USAGE = SHORT_MESSAGE_USAGE + "\nExample: " + COMMAND_WORD;

    public static final List<Class<? extends Command>> AVAILABLE_COMMANDS = List.of(
            ListCommand.class, AddCommand.class, EditCommand.class, DeleteCommand.class,
            FindCommand.class, AddTagsCommand.class, RemoveTagCommand.class,
            HelpCommand.class, ClearCommand.class, ExitCommand.class);

    private String getCommandUsageMessage(Class<? extends Command> commandClass) {
        try {
            return (String) commandClass.getField("SHORT_MESSAGE_USAGE").get("");
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public CommandResult execute(Model model) {
        String usageMessages = AVAILABLE_COMMANDS.stream()
                .map(this::getCommandUsageMessage)
                .filter(message -> !message.isBlank())
                .collect(Collectors.joining("\n"));
        return new CommandResult(usageMessages);
    }
}
