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

    LoginService loginService;

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

    }
}
