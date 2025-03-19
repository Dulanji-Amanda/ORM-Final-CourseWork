package lk.ijse.dao.custom;

import lk.ijse.entity.User;
import org.hibernate.Session;

public interface UserDAO extends CrudDAO <User>{
    User getUserByUsername(String username);

}
