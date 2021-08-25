package pl.kemot.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import pl.kemot.EmailManager;
import pl.kemot.model.EmailAccount;
import pl.kemot.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageWindowController extends BaseController implements Initializable {

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<EmailAccount> senderChoiceBox;

    @FXML
    void sendBtnAction() {
        System.out.println("Send btn action");
        System.out.println(htmlEditor.getHtmlText());
    }

    public ComposeMessageWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        super(emailManager, viewFactory, fxmlFileName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpSenderChoiceBox();
    }

    private void setUpSenderChoiceBox() {
        senderChoiceBox.setItems(emailManager.getEmailAccounts());
        senderChoiceBox.setValue(emailManager.getEmailAccounts().get(0));
    }
}
