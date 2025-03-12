package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyRecruitTrackPro;

/**
 * A class to access RecruitTrackPro data stored as a json file on the hard disk.
 */
public class JsonRecruitTrackProStorage implements RecruitTrackProStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRecruitTrackProStorage.class);

    private Path filePath;

    public JsonRecruitTrackProStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRecruitTrackProFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro() throws DataLoadingException {
        return readRecruitTrackPro(filePath);
    }

    /**
     * Similar to {@link #readRecruitTrackPro()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableRecruitTrackPro> jsonRecruitTrackPro = JsonUtil.readJsonFile(
                filePath, JsonSerializableRecruitTrackPro.class);
        if (!jsonRecruitTrackPro.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRecruitTrackPro.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveRecruitTrackPro(ReadOnlyRecruitTrackPro RecruitTrackPro) throws IOException {
        saveRecruitTrackPro(RecruitTrackPro, filePath);
    }

    /**
     * Similar to {@link #saveRecruitTrackPro(ReadOnlyRecruitTrackPro)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro, Path filePath) throws IOException {
        requireNonNull(recruitTrackPro);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRecruitTrackPro(recruitTrackPro), filePath);
    }

}
