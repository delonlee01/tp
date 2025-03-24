package seedu.recruittrackpro.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.recruittrackpro.commons.core.GuiSettings;
import seedu.recruittrackpro.commons.core.LogsCenter;
import seedu.recruittrackpro.logic.commands.Command;
import seedu.recruittrackpro.logic.commands.CommandResult;
import seedu.recruittrackpro.logic.commands.exceptions.CommandException;
import seedu.recruittrackpro.logic.parser.RecruitTrackProParser;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ReadOnlyRecruitTrackPro;
import seedu.recruittrackpro.model.person.Person;
import seedu.recruittrackpro.model.person.UniquePersonList;
import seedu.recruittrackpro.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final RecruitTrackProParser recruitTrackProParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        recruitTrackProParser = new RecruitTrackProParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = recruitTrackProParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveRecruitTrackPro(model.getRecruitTrackPro());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyRecruitTrackPro getRecruitTrackPro() {
        return model.getRecruitTrackPro();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public boolean isAscending() {
        return model.isAscending();
    }

    @Override
    public Path getRecruitTrackProFilePath() {
        return model.getRecruitTrackProFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
