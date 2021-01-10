package group0110.ui.gui;

import group0110.gateways.GatewaySystem;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainController extends Application {
    private Model model;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the program with the saved information
     *
     * @param stage a window that is created for the start up
     * @throws IOException thrown when an error occurs when trying to start up
     */

    @Override
    public void start(Stage stage) throws IOException {
        GatewaySystem gs = new GatewaySystem("users.ser", "events.ser", "rooms.ser");
        model = new Model(gs);
        model.init();
        Platform.setImplicitExit(false);
        // Create instance of the CreateAccount View
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/fxml/signupMenu.fxml"));
        // Create instance of CreateAccountController
        SignupController signupController = new SignupController(model);
        // Set the instance
        loader.setController(signupController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Create Account");
        stage.setScene(scene);

        // Show the window
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println("Signup Window Closed");
                stop();
            }
        });
    }

    /**
     * Stops the GUI, happens when the GUI exits
     */

    @Override
    public void stop(){
        // Runs when the GUI exits.
        System.out.println("Program is closing");
        model.save();
        Platform.exit();
    }
}
