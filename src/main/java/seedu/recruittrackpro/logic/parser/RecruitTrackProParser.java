package seedu.recruittrackpro.logic.parser;

import static seedu.recruittrackpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruittrackpro.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.recruittrackpro.commons.core.LogsCenter;
import seedu.recruittrackpro.logic.commands.AddCommand;
import seedu.recruittrackpro.logic.commands.AddTagsCommand;
import seedu.recruittrackpro.logic.commands.ClearCommand;
import seedu.recruittrackpro.logic.commands.Command;
import seedu.recruittrackpro.logic.commands.DeleteCommand;
import seedu.recruittrackpro.logic.commands.EditCommand;
import seedu.recruittrackpro.logic.commands.EditTagCommand;
import seedu.recruittrackpro.logic.commands.ExitCommand;
import seedu.recruittrackpro.logic.commands.FindCommand;
import seedu.recruittrackpro.logic.commands.HelpCommand;
import seedu.recruittrackpro.logic.commands.ListCommand;
import seedu.recruittrackpro.logic.commands.RemoveTagCommand;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class RecruitTrackProParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(RecruitTrackProParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AddTagsCommand.COMMAND_WORD:
            return new AddTagsCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);

        case EditTagCommand.COMMAND_WORD:
            return new EditTagCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
