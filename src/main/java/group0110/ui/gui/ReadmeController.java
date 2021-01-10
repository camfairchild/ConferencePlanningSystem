package group0110.ui.gui;

import com.vladsch.flexmark.ast.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

class ReadmeController extends GUIController{
    @FXML
    private WebView wbView;

    ReadmeController(Model model) {
        super(model);
    }

    @FXML
    void handleBack(ActionEvent event) {
        handleExit(event);
    }

    @FXML private void initialize() {
        try {
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();

            URL url = getClass().getResource("/README.md"); // Get file URL
            File file = new File(url.toURI()); // Convert URL to File using URI
            Reader fileReader = new FileReader(file); // Make FileReader for file

            Node document = parser.parseReader(fileReader); // Parse the file from Reader
            String html = renderer.render(document);  // render MD as html string
            String out_html = "<div id='content'>" + html + "</div>"; // Wrap html in div for css
            WebEngine webEngine = wbView.getEngine();
            webEngine.loadContent(out_html); // Show html string in webview
            webEngine.setUserStyleSheetLocation(
                    getClass().getResource("/readme.css").toString()); // apply css to html
        } catch (URISyntaxException | IOException e) {
            openError(e);
        }
    }
}
