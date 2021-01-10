package group0110.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

abstract class SecondaryUserMenuController extends UserMenuController {
    private Stage previousMenu;

     SecondaryUserMenuController(Stage previousMenu, Model model) {
        super(model);
        this.previousMenu = previousMenu;
    }

    @Override
    @FXML
    void handleBack(ActionEvent event) {
        back();
    }

    void back() {
        previousMenu.show();
        getStage().close();
    }

    protected void openMenu(Stage parentStage, String fxml, String title, SecondaryUserMenuController controller) {
        Stage newStage =  super.openNewWindowAndClose(parentStage, fxml, title, controller);
        newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                controller.back();
            }
        });
    }
}
