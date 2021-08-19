package pl.kemot.controller;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.kemot.EmailManager;
import pl.kemot.controller.services.LoginService;
import pl.kemot.model.EmailAccount;
import pl.kemot.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField emailAdressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        super(emailManager, viewFactory, fxmlFileName);
    }

    @FXML
    void loginBtnAction() {
        System.out.println("Login button action!");
        if(areFieldsValid()){
            EmailAccount emailAccount = new EmailAccount(emailAdressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            //We have made loginService extends Service from java.concurent so now it will be executen in another thread so we need to start this tread
            loginService.start();
            // Then we use setOnSucceeded() method to execute the rest of login logic only in case when we started new thread succesfully
            loginService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    // Now in order to take value of login method we need to invoke getValue() method onloginService object
                    EmailLoginResult loginResult = loginService.getValue();

                    switch(loginResult) {
                        case SUCCESS:
                            if(!viewFactory.isMainViewInitialized()){
                                viewFactory.showMainWindow();
                            }
                            Stage stage = (Stage)emailAdressField.getScene().getWindow();
                            viewFactory.closeStage(stage);
                        case FAILED_BY_CREDENTIALS:
                            errorLabel.setText("invalid credentials");
                        case FAILED_BY_UNEXPECTED_ERROR:
                            errorLabel.setText("unexpected error");
                        case FAILED_BY_NETWORK:
                            errorLabel.setText("network failer");
                        default:
                            System.out.println(loginResult);
                    }
                }
            });

        }
    }

    private boolean areFieldsValid() {
        if(emailAdressField.getText().isEmpty()){
            errorLabel.setText("Please fill the adress field");
            return false;
        } else if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill the password field");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAdressField.setText("kemot.test@gmail.com");
        passwordField.setText("Kemot123!");
    }
}
