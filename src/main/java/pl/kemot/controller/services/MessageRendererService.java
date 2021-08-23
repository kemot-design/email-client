package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;
import pl.kemot.model.EmailMessage;

public class MessageRendererService extends Service {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        stringBuffer = new StringBuffer();
    }

    // we will make one instance of RendererService and we will pass different messages to display, so we need a method to set the message
    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    @Override
    protected Task createTask() {
        return null;
    }
}
