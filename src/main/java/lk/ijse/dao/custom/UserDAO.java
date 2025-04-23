package lk.ijse.dao.custom;

import lk.ijse.entity.User;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO <User>{
    User getUserByUsername(String username);

    boolean isEmailExists(String email);

}