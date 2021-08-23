package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.web.WebEngine;
import pl.kemot.model.EmailMessage;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

public class MessageRendererService extends Service {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        stringBuffer = new StringBuffer();
        //As loading message is a long task we want to display message after everything will be loaded, so we use setOnSucceded method 
        setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                displayMessage();
            }
        });
    }

    // we will make one instance of RendererService and we will pass different messages to display, so we need a method to set the message
    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                loadMessage();
                return null;
            }
        };
    }

    private void loadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0);  //as we will display multiple messages we need to clear the stringBuffer each time
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();

        if(isSimpleType(contentType)){
            stringBuffer.append(message.getContent().toString());
        } else if (isMultipartType(contentType)){
            Multipart multipart = (Multipart)message.getContent();
            // multipart is an array so we need to iterate through it
            for (int i = multipart.getCount() - 1 ; i >= 0 ; i--){
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if(isSimpleType(bodyPartContentType)){
                    stringBuffer.append(bodyPart.getContent().toString());
                }
            }
        }
    }

    private boolean isSimpleType(String contentType){
        if(contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed") ||
                contentType.contains("text")){
            return true;
        } else {
            return false;
        }
    }

    private boolean isMultipartType(String contentType){
        if(contentType.contains("multipart")){
            return true;
        } else {
            return false;
        }
    }

    // we create another method to load message to our webengine and we will invoke this as the loading will be finished so in setOnSucceded method - in the constructor
    private void displayMessage(){
        webEngine.loadContent(stringBuffer.toString());
    }
}
