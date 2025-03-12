package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyRecruitTrackPro;
import seedu.address.model.RecruitTrackPro;

/**
 * Represents a storage for {@link RecruitTrackPro}.
 */
public interface RecruitTrackProStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRecruitTrackProFilePath();

    /**
     * Returns RecruitTrackPro data as a {@link ReadOnlyRecruitTrackPro}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro() throws DataLoadingException;

    /**
     * @see #getRecruitTrackProFilePath()
     */
    Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyRecruitTrackPro} to the storage.
     * @param recruitTrackPro cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro) throws IOException;

    /**
     * @see #saveRecruitTrackPro(ReadOnlyRecruitTrackPro)
     */
    void saveRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro, Path filePath) throws IOException;

}
