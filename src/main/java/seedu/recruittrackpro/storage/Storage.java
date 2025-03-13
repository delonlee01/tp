package seedu.recruittrackpro.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recruittrackpro.commons.exceptions.DataLoadingException;
import seedu.recruittrackpro.model.ReadOnlyRecruitTrackPro;
import seedu.recruittrackpro.model.ReadOnlyUserPrefs;
import seedu.recruittrackpro.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RecruitTrackProStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getRecruitTrackProFilePath();

    @Override
    Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro() throws DataLoadingException;

    @Override
    void saveRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro) throws IOException;

}
