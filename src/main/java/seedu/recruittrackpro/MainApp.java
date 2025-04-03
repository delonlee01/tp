package seedu.recruittrackpro;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.recruittrackpro.commons.core.Config;
import seedu.recruittrackpro.commons.core.LogsCenter;
import seedu.recruittrackpro.commons.core.Version;
import seedu.recruittrackpro.commons.exceptions.DataLoadingException;
import seedu.recruittrackpro.commons.util.ConfigUtil;
import seedu.recruittrackpro.commons.util.StringUtil;
import seedu.recruittrackpro.logic.Logic;
import seedu.recruittrackpro.logic.LogicManager;
import seedu.recruittrackpro.model.Model;
import seedu.recruittrackpro.model.ModelManager;
import seedu.recruittrackpro.model.ReadOnlyRecruitTrackPro;
import seedu.recruittrackpro.model.ReadOnlyUserPrefs;
import seedu.recruittrackpro.model.RecruitTrackPro;
import seedu.recruittrackpro.model.UserPrefs;
import seedu.recruittrackpro.model.util.SampleDataUtil;
import seedu.recruittrackpro.storage.JsonRecruitTrackProStorage;
import seedu.recruittrackpro.storage.JsonUserPrefsStorage;
import seedu.recruittrackpro.storage.RecruitTrackProStorage;
import seedu.recruittrackpro.storage.Storage;
import seedu.recruittrackpro.storage.StorageManager;
import seedu.recruittrackpro.storage.UserPrefsStorage;
import seedu.recruittrackpro.ui.Ui;
import seedu.recruittrackpro.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(3, 0, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing RecruitTrackPro ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        RecruitTrackProStorage recruitTrackProStorage =
                new JsonRecruitTrackProStorage(userPrefs.getRecruitTrackProFilePath());
        storage = new StorageManager(recruitTrackProStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s RecruitTrackPro and {@code userPrefs}. <br>
     * The data from the sample RecruitTrackPro will be used instead if {@code storage}'s RecruitTrackPro is not found,
     * or an empty RecruitTrackPro will be used instead if errors occur when reading {@code storage}'s RecruitTrackPro.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getRecruitTrackProFilePath());

        Optional<ReadOnlyRecruitTrackPro> recruitTrackProOptional;
        ReadOnlyRecruitTrackPro initialData;
        try {
            recruitTrackProOptional = storage.readRecruitTrackPro();
            if (!recruitTrackProOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getRecruitTrackProFilePath()
                        + " populated with a sample RecruitTrackPro.");
            }
            initialData = recruitTrackProOptional.orElseGet(SampleDataUtil::getSampleRecruitTrackPro);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getRecruitTrackProFilePath() + " could not be loaded."
                    + " Will be starting with an empty RecruitTrackPro.");
            initialData = new RecruitTrackPro();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting RecruitTrackPro " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping RecruitTrackPro ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
