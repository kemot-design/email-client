package pl.kemot.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kemot.EmailManager;
import pl.kemot.controller.EmailLoginResult;
import pl.kemot.model.EmailAccount;

import javax.mail.*;

//We use Service class from javafx.concurrent library to use multithreading in our app
// the value in the <> is a value type that will be returned in task method
public class LoginService extends Service<EmailLoginResult> {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginResult login(){
        // we need to create authenticator, javax.mail requires it
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount.getAdress(), emailAccount.getPassword());
            }
        };
        // Since we have the authenticator we can create a session and get store from our email provider
        // Creating new session is like a Singelton Design Pattern
        // Now a lot of things can go wrong, so we have to surround with try catch
        try {
            // we use thread sleep to showcase that one thread aplication is not a good idea, beause everything is blocked while sleep is going on
            //Thread.sleep(10000);
            Session session = Session.getInstance(emailAccount.getProperties(), authenticator);
            Store store = session.getStore("imaps");
            store.connect(emailAccount.getProperties().getProperty("incomingHost"), emailAccount.getAdress(), emailAccount.getPassword());
            // when we connect to the store we can put it to our eamilAccount and we will use it to get emails
            emailAccount.setStore(store);
            emailManager.addEmailAccount(emailAccount);
            emailManager.showEmailsFolders(emailAccount);

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_NETWORK;
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_CREDENTIALS;
        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        } catch(Error e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<EmailLoginResult>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }
}
