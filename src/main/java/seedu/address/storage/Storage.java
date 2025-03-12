package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyRecruitTrackPro;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

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
