package pl.kemot.controller;

import pl.kemot.EmailManager;
import pl.kemot.view.ViewFactory;

public abstract class BaseController {

    private EmailManager emailManager;
    private ViewFactory viewFactory;
    private String fxmlFileName;

    public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFileName) {
        this.emailManager = emailManager;
        this.viewFactory = viewFactory;
        this.fxmlFileName = fxmlFileName;
    }
}
