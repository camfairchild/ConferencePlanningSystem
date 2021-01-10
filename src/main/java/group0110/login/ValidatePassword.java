package group0110.login;

import group0110.entities.User;
import group0110.usecases.user.UserManager;


public class ValidatePassword {

    private String password;
    private String username;
    private UserManager uM;
    /**
     * Checks if the password is Valid
     *
     * @param inputPassword  the password being checked
     * @param inputUsername     the username associated with the password
     * @param inputUM UserManager of the current UserManager
     */
    public ValidatePassword(String inputPassword, String inputUsername, UserManager inputUM){
        this.password = inputPassword;
        this.username = inputUsername;
        this.uM = inputUM;
    }

    /**
     * Checking if the given password is the same as the password associated with the given username
     * @return boolean
     */
    public boolean validate(){
        User user = uM.getUser(username);
        if (user == null) {
            return false;
        } else {
            return uM.getUser(username).getPassword().equals(password);
        }
    }

}
