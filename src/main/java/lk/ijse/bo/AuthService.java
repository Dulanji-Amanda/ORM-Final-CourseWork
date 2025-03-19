package lk.ijse.bo;

import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.entity.User;
import lk.ijse.util.Role;
import lk.ijse.util.PasswordEncryptionUtil;

public class AuthService {
    private final UserDAO userDAO = new UserDAOImpl();

    public Role authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && PasswordEncryptionUtil.checkPassword(password, user.getPassword())) {
            return user.getRole();
        }
        return null;
    }
}
