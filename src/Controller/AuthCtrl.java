package Controller;

import Model.Account;
import Server.AuthQuery;

public class AuthCtrl {
    public static boolean changeInfomation(Account account) {
        return AuthQuery.changeInformation(account);
    }

    public static boolean changePassword(int userId, String password) {
        return AuthQuery.changePassword(userId, password);
    }

    public static boolean checkPassword(int userId, String password) {
        return AuthQuery.checkPassword(userId, password);
    }

    public static Account getUser(int userId) {
        return AuthQuery.getUser(userId);
    }

    public static Account login(String username, String password) {
        return AuthQuery.login(username, password);
    }

    public static int signUp(Account account, String username, String password) {
        return AuthQuery.signUp(account, username, password);
    }
}
