package lk.ijse.dao.custom.impl;

import lk.ijse.bo.exception.DuplicateException;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public boolean save(Payment entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();

        try{
            Payment existPayment = session.get(Payment.class, entity.getPaymentId());

            if(existPayment != null){
                throw new DuplicateException("Payment id duplicated");
            }
            session.persist(entity);
            tx.commit();
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<Payment> paymentList = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            paymentList = session.createQuery("FROM Payment", Payment.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return paymentList;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        // Get the last user ID from the database
        String lastId = session.createQuery("SELECT pa.id FROM Payment pa ORDER BY pa.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("PA00-%03d", numericPart);
        } else {
            return "PA00-001"; // First user ID
        }
    }

    @Override
    public Payment findById(String id) {
        return null;
    }

}