package lk.ijse.dao.custom.impl;

import lk.ijse.bo.exception.DuplicateException;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    @Override
    public boolean save(Patient entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();

        try{
            Patient existPatient = session.get(Patient.class, entity.getId());

            if(existPatient != null){
                throw new DuplicateException("Patient id duplicated");
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
    public boolean update(Patient entity) {
        Transaction transaction = null;
        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            // Update the patient entity
            session.merge(entity); // Use merge to handle detached entities
            transaction.commit();
            return true; // Return true if the transaction is successful
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Transaction transaction = null;
        try (Session session = factoryConfiguration.getSession()) {
            transaction = session.beginTransaction();

            // Fetch the patient entity by ID
            Patient patient = session.get(Patient.class, id);
            if (patient != null) {
                session.delete(patient); // Delete the patient entity
                transaction.commit();
                return true; // Return true if the transaction is successful
            }
            return false; // Return false if the patient does not exist
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }

    @Override
    public List<Patient> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<Patient> query = session.createQuery("FROM Patient ", Patient.class);
        ArrayList<Patient> patients = (ArrayList<Patient>) query.list();
        return patients;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        // Get the last user ID from the database
        String lastId = session.createQuery("SELECT p.id FROM Patient p ORDER BY p.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("P00-%03d", numericPart);
        } else {
            return "P00-001"; // First user ID
        }
    }

    @Override
    public Patient findById(String id) {
        Session session = factoryConfiguration.getSession();
        Patient patient = session.get(Patient.class, id);
        session.close();
        return patient;
    }

}