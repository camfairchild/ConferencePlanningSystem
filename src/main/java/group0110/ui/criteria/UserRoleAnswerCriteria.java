package group0110.ui.criteria;

import group0110.entities.User;
import group0110.usecases.user.UserManager;

public class UserRoleAnswerCriteria extends UserTypeAnswerCriteria {
    private User user;

    public UserRoleAnswerCriteria(String username, UserManager um) {
        super();
        this.user = um.getUser(username);
    }

    @Override
    public boolean check(String role) {
        return super.check(role) && user.getRole().toString().toLowerCase().equals(role);
    }
}
