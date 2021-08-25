package pl.kemot.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import pl.kemot.EmailManager;
import pl.kemot.controller.services.EmailMessageSenderService;
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
        EmailMessageSenderService senderService = new EmailMessageSenderService(
                senderChoiceBox.getValue(),
                recipientTextField.getText(),
                subjectTextField.getText(),
                htmlEditor.getHtmlText()
        );
        senderService.start();
        senderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                MessageSendResult sendResult = senderService.getValue();
                switch (sendResult){
                    case SUCCESS:
                        Stage stage = (Stage)recipientTextField.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        break;
                    case FAILED_BY_PROVIDER:
                        errorLabel.setText("Failed by provider error");
                        break;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("Failed by unexpected error");
                        break;
                    default:
                        errorLabel.setText("Failed by unknown error");
                        break;
                }
            }
        });
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
