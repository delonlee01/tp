package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalRecruitTrackPro;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.RecruitTrackPro;
import seedu.address.model.ReadOnlyRecruitTrackPro;

public class JsonRecruitTrackProStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRecruitTrackProStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readRecruitTrackPro_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readRecruitTrackPro(null));
    }

    private java.util.Optional<ReadOnlyRecruitTrackPro> readRecruitTrackPro(String filePath) throws Exception {
        return new JsonRecruitTrackProStorage(Paths.get(filePath)).readRecruitTrackPro(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecruitTrackPro("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readRecruitTrackPro("notJsonFormatRecruitTrackPro.json"));
    }

    @Test
    public void readRecruitTrackPro_invalidPersonRecruitTrackPro_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readRecruitTrackPro("invalidPersonRecruitTrackPro.json"));
    }

    @Test
    public void readRecruitTrackPro_invalidAndValidPersonRecruitTrackPro_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readRecruitTrackPro("invalidAndValidPersonRecruitTrackPro.json"));
    }

    @Test
    public void readAndSaveRecruitTrackPro_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempRecruitTrackPro.json");
        RecruitTrackPro original = getTypicalRecruitTrackPro();
        JsonRecruitTrackProStorage jsonRecruitTrackProStorage = new JsonRecruitTrackProStorage(filePath);

        // Save in new file and read back
        jsonRecruitTrackProStorage.saveRecruitTrackPro(original, filePath);
        ReadOnlyRecruitTrackPro readBack = jsonRecruitTrackProStorage.readRecruitTrackPro(filePath).get();
        assertEquals(original, new RecruitTrackPro(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonRecruitTrackProStorage.saveRecruitTrackPro(original, filePath);
        readBack = jsonRecruitTrackProStorage.readRecruitTrackPro(filePath).get();
        assertEquals(original, new RecruitTrackPro(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonRecruitTrackProStorage.saveRecruitTrackPro(original); // file path not specified
        readBack = jsonRecruitTrackProStorage.readRecruitTrackPro().get(); // file path not specified
        assertEquals(original, new RecruitTrackPro(readBack));

    }

    @Test
    public void saveRecruitTrackPro_nullRecruitTrackPro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecruitTrackPro(null, "SomeFile.json"));
    }

    /**
     * Saves {@code recruitTrackPro} at the specified {@code filePath}.
     */
    private void saveRecruitTrackPro(ReadOnlyRecruitTrackPro recruitTrackPro, String filePath) {
        try {
            new JsonRecruitTrackProStorage(Paths.get(filePath))
                    .saveRecruitTrackPro(recruitTrackPro, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecruitTrackPro_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveRecruitTrackPro(new RecruitTrackPro(), null));
    }
}
