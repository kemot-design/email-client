package pl.kemot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.kemot.EmailManager;
import pl.kemot.controller.services.LoginService;
import pl.kemot.model.EmailAccount;
import pl.kemot.view.ViewFactory;

public class LoginWindowController extends BaseController{

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
        if(areFieldsValid()){
            EmailAccount emailAccount = new EmailAccount(emailAdressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult loginResult = loginService.login();

            if (loginResult == EmailLoginResult.SUCCESS) {
                viewFactory.showMainWindow();
                Stage stage = (Stage)emailAdressField.getScene().getWindow();
                viewFactory.closeStage(stage);
            } else {
                System.out.println(loginResult);
            }
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
}
