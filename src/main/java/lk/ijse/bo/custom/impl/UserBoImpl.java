package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBo;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordEncryptionUtil;
import lk.ijse.util.Role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {
    private final UserDAO userDAO = new UserDAOImpl();

    public boolean registerUser(String username,String email, String password, Role role) {
        if (userDAO.getUserByUsername(username) != null) {
            return false; // User already exists
        }

        String hashedPassword = PasswordEncryptionUtil.hashPassword(password);
        String newUserId = userDAO.getNextId(); // Generate new ID
        User user = new User(newUserId, username,email, hashedPassword, role);
        userDAO.save(user);
        return true;
    }

    @Override
    public ArrayList<String> getAllRoll() {
        return userDAO.getAllRolls();
    }

    @Override
    public User findByRoll(String selectedRoll) throws SQLException, ClassNotFoundException {
        return userDAO.findByRoll(selectedRoll);
    }

    @Override
    public boolean isEmailExists(String email){
        return userDAO.isEmailExists(email);
    }

    @Override
    public List<UserDTO> getAllUser() {
        return List.of();
    }

    @Override
    public void updateUser(UserDTO user) {

    }

}
