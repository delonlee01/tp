package seedu.recruittrackpro.ui;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.recruittrackpro.logic.commands.CommandResult;
import seedu.recruittrackpro.logic.commands.exceptions.CommandException;
import seedu.recruittrackpro.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final LinkedList<ExecutedCommand> history = new LinkedList<>();
    private ExecutedCommand referenceCommand;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
            referenceCommand = null;

            ExecutedCommand commandEntered = new ExecutedCommand(commandText);
            if (!history.isEmpty()) {
                ExecutedCommand lastCommandEntered = history.getLast();
                lastCommandEntered.setNext(commandEntered);
                commandEntered.setPrevious(lastCommandEntered);
            }
            history.offerLast(commandEntered);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    private void handleUpKey() {
        if (referenceCommand == null) {
            referenceCommand = history.getLast();
            commandTextField.setText(referenceCommand.getValue());
            return;
        }

        ExecutedCommand previous = referenceCommand.getPrevious();
        if (previous == null) {
            return;
        }

        referenceCommand = previous;
        commandTextField.setText(referenceCommand.getValue());
    }

    private void handleDownKey() {
        if (referenceCommand == null) {
            return;
        }

        ExecutedCommand next = referenceCommand.getNext();
        if (next == null) {
            commandTextField.setText("");
            referenceCommand = null;
            return;
        }

        referenceCommand = next;
        commandTextField.setText(referenceCommand.getValue());
    }

    private void handleKeyPressed(KeyEvent event) {
        String keyPressed = event.getCode().toString();
        if ((!keyPressed.equals("UP") && !keyPressed.equals("DOWN"))) {
            return;
        }
        event.consume(); // to override default behaviour

        if (history.isEmpty()) {
            return;
        }

        if (keyPressed.equals("UP")) {
            handleUpKey();
        } else {
            handleDownKey();
        }

        commandTextField.end();
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.recruittrackpro.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    private static class ExecutedCommand {

        private final String value;
        private ExecutedCommand previous;
        private ExecutedCommand next;

        public ExecutedCommand(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public ExecutedCommand getPrevious() {
            return this.previous;
        }

        public ExecutedCommand getNext() {
            return this.next;
        }

        public void setPrevious(ExecutedCommand previous) {
            this.previous = previous;
        }

        public void setNext(ExecutedCommand next) {
            this.next = next;
        }

    }

}
