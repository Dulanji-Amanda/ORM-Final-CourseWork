package lk.ijse.bo;

import lk.ijse.bo.custom.UserBo;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordEncryptionUtil;
import lk.ijse.util.Role;

public class UserBoImpl implements UserBo {
    private final UserDAO userDAO = new UserDAOImpl();

    public boolean registerUser(String username, String password, Role role) {
        if (userDAO.getUserByUsername(username) != null) {
            return false; // User already exists
        }

        String hashedPassword = PasswordEncryptionUtil.hashPassword(password);
        String newUserId = userDAO.getNextId(); // Generate new ID
        User user = new User(newUserId, username, hashedPassword, role);
        userDAO.save(user);
        return true;
    }
}
