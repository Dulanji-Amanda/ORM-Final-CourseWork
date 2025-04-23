package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.controller.LoginPageController;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl  implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    public String getNextId() {
        try (Session session = factoryConfiguration.getSession()) {
            // Get the last user ID from the database
            String lastId = session.createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", String.class)
                    .setMaxResults(1)
                    .uniqueResult();

            if (lastId != null) {
                int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
                return String.format("U00-%03d", numericPart);
            } else {
                return "U00-001"; // First user ID
            }
        }
    }

    @Override
    public User findById(String id) {
        return null;
    }

    public User getUserByUsername(String username) {
        try (Session session = factoryConfiguration.getSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        return false;
    }

    @Override
    public boolean save(User user) {
        Transaction transaction = null;
        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true; // Return true if the transaction is successful
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    @Override
    public boolean update(User entity) {
        Transaction transaction = null;
        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();
            session.merge(entity); // Use merge to handle detached entities
            transaction.commit();
            LoginPageController.username = entity.getUsername();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

}