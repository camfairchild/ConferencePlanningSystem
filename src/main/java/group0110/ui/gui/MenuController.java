package group0110.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

abstract class MenuController extends GUIController {
    protected MenuController(Model model) {
        super(model);
    }

    @FXML
    protected void handleReadme(ActionEvent event) {
        ReadmeController readmeController = new ReadmeController(model);
        openNewWindow("readmeMenu", "README", readmeController);
    }
}
