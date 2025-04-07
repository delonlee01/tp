package seedu.recruittrackpro.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.recruittrackpro.commons.core.LogsCenter;

/**
 * Controller for the user guide page
 */
public class UserGuideWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2425s2-cs2103-f15-3.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;

    private static final Logger logger = LogsCenter.getLogger(UserGuideWindow.class);
    private static final String FXML = "UserGuideWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new UserGuideWindow.
     *
     * @param root Stage to use as the root of the UserGuideWindow.
     */
    public UserGuideWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new UserGuideWindow.
     */
    public UserGuideWindow() {
        this(new Stage());
    }

    /**
     * Shows the user guide window.
     *
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing user guide page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the user guide window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the user guide window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the user guide window.
     */
    public void focus() {
        getRoot().setIconified(false);
        getRoot().requestFocus();
    }

    /**
     * Updates the UserGuideWindow's theme by setting a new stylesheet.
     * Checks if the Stage and Scene are properly initialized before applying the stylesheet.
     * Logs a severe error if either the Stage or Scene is not initialized.
     *
     * @param newStyleSheet The path to the new stylesheet to apply. Must be a valid path accessible at runtime.
     */
    public void updateTheme(String newStyleSheet) {
        // Ensure that the Stage is available and get the Scene from it
        if (getRoot() != null && getRoot().getScene() != null) {
            ObservableList<String> stylesheets = getRoot().getScene().getStylesheets();
            stylesheets.clear();
            stylesheets.add(newStyleSheet);
        } else {
            logger.severe("Stage or Scene is not properly initialized in UserGuideWindow");
        }
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
