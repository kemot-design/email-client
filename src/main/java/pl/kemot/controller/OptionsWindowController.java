package pl.kemot.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import pl.kemot.EmailManager;
import pl.kemot.view.ViewFactory;

public class OptionsWindowController extends BaseController {

    public OptionsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        super(emailManager, viewFactory, fxmlFileName);
    }

    @FXML
    private Slider fontSizePicker;

    @FXML
    private ChoiceBox<?> themePicker;

    @FXML
    void applyBtnAction() {

    }

    @FXML
    void cancelBtnAcction() {

    }
}
