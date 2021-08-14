package pl.kemot.controller.services;

import pl.kemot.EmailManager;
import pl.kemot.controller.EmailLoginResult;
import pl.kemot.model.EmailAccount;

public class LoginService {

    EmailAccount emailAccount;
    EmailManager emailManager;

    public LoginService(EmailAccount emailAccount, EmailManager emailManager) {
        this.emailAccount = emailAccount;
        this.emailManager = emailManager;
    }

    public EmailLoginResult login(){
        if(emailAccount.getAdress() )
    }
}
