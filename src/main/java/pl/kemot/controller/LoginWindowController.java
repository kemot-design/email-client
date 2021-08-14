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
        System.out.println("Logging in....");

        loginService = new LoginService(new EmailAccount(emailAdressField.getText(), passwordField.getText()), emailManager);
        EmailLoginResult loginResult = loginService.login();

        if(loginResult == EmailLoginResult.SUCCESS) {
            viewFactory.showMainWindow();
            Stage stage = (Stage) errorLabel.getScene().getWindow(); // get any field in the stage and get the window out of this, this is way areound because there is no build in method to get a scene in JavaFX
            viewFactory.closeStage(stage);
        } else {
            System.out.println("Nie udało sie zalogowac");
            System.out.println(loginResult);
        }


    }
}
