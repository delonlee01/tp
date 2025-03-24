package seedu.recruittrackpro.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.recruittrackpro.commons.core.GuiSettings;
import seedu.recruittrackpro.logic.commands.CommandResult;
import seedu.recruittrackpro.logic.commands.exceptions.CommandException;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;
import seedu.recruittrackpro.model.ReadOnlyRecruitTrackPro;
import seedu.recruittrackpro.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the RecruitTrackPro.
     *
     * @see seedu.recruittrackpro.model.Model#getRecruitTrackPro()
     */
    ReadOnlyRecruitTrackPro getRecruitTrackPro();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Switches sorting between ascending and descending */
    void switchPersonListSorting();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getRecruitTrackProFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
