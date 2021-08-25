package pl.kemot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import pl.kemot.EmailManager;
import pl.kemot.view.ViewFactory;

public class ComposeMessageWindowController extends BaseController {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    void sendBtnAction() {
        System.out.println("Send btn action");
        System.out.println(htmlEditor.getHtmlText());
    }

    public ComposeMessageWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        super(emailManager, viewFactory, fxmlFileName);
    }
}
