package seedu.recruittrackpro.model;

import java.nio.file.Path;

import seedu.recruittrackpro.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getRecruitTrackProFilePath();

}
