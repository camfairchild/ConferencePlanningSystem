package group0110.ui.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public abstract class GUIController {
    Model model;
    @FXML
    AnchorPane anchorPane;

    protected GUIController(Model model) {
        this.model = model;
    }

    @FXML
    protected void handleExit(ActionEvent event) {
        exit();
    }

    protected void exit() {
        // Cast to the event source to Node.
        // Then, cast the window of the Node to Stage.
        // Stage stage = getStage(anchorPane);
        // stage.close();
        Platform.exit();
    }

    private Stage openWindow(String fxml, String title, GUIController controller) throws IOException {
        Parent root = null;
        FXMLLoader load = new FXMLLoader(getClass().getResource("/javafx/fxml/" + fxml + ".fxml"));
        load.setController(controller);
        root = load.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        // Show new window
        stage.show();
        return stage;
    }

    protected Stage openNewWindowAndClose(Stage parentStage, String fxml, String title, GUIController controller) {
        try {
            Stage newStage = openWindow(fxml, title, controller);
            // Hide current window
            parentStage.hide();
            return newStage;
        } catch (IOException e) {
            openError(e);
        }
        return parentStage;
    }

    protected void openNewWindow(String fxml, String title, GUIController controller) {
        try {
            // open scene in new window/stage
            openWindow(fxml, title, controller);
        } catch (IOException e) {
            openError(e);
        }
    }

    /**
     * A dialogue for confirming if a user wants to perform an action.
     *
     * @param action Description of the action that the user should confirm
     * @return Returns the button type of the button that the user presses.
     */
    protected Optional<ButtonType> getConfirmationDialogue(String action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm the action.");
        alert.setContentText(action);

        return alert.showAndWait();
    }

    /**
     * Opens a dialogue and displays a messageinforming the users that an action was
     * successful.
     *
     * @param msg The message to display in the dialogue.
     */
    protected void openSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice");
        alert.setContentText(msg);

        alert.showAndWait();
    }

    /**
     * Opens an error dialog from the exception e
     *
     * @param e the Exception.
     */
    protected void openError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An Exception has Occurred!!");
        alert.setContentText(e.getMessage());

        // Converting stacktrace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String exceptionText = stringWriter.toString();

        // Making the content for the dialog box
        Label label = new Label("The stacktrace for the exception was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        // Will make sure the stacktrace is seen
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(label, 0, 0);
        gridPane.add(textArea, 0, 1);

        // Set expandable content as gridPane
        alert.getDialogPane().setExpandableContent(gridPane);

        // Show alert
        alert.showAndWait();
    }

    Stage getStage(Node node) {
        // Get the stage from the Node node
        Stage stage = (Stage) node.getScene().getWindow();
        return stage;
    }

    Stage getStage() {
        // Get the stage from the Node node
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        return stage;
    }
}
