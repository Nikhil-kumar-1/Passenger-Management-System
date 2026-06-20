package src.service;

import src.dao.LoginDAO;
import src.model.Login;

public class LoginService {

    LoginDAO dao = new LoginDAO();

    public void register(Login login) {

        dao.register(login);
    }

    public Login login(String email,
                       String password) {

        return dao.login(email,password);
    }
}