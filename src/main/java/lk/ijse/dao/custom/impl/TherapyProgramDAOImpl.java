package lk.ijse.dao.custom.impl;

import lk.ijse.bo.exception.DuplicateException;
import lk.ijse.bo.exception.NotFoundException;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.TherapyProgramDAO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();

    @Override
    public boolean save(TherapyProgram entity) {
        Session session = factoryConfiguration.getSession();
        Transaction tx = session.beginTransaction();

        try{
            Therapist existsTherapist = session.get(Therapist.class, entity.getProgramId());
            if (existsTherapist != null) {
                throw new DuplicateException("Therapist id duplicated");
            }
            session.persist(entity);
            tx.commit();
            return true;
        }catch(Exception e){
            tx.rollback();
            return false;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public boolean update(TherapyProgram entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(entity);//update
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            if (therapyProgram == null) {
                throw new NotFoundException("Therapy Program not found");
            }
            session.remove(therapyProgram);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<TherapyProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram ", TherapyProgram.class);
        ArrayList<TherapyProgram> therapyPrograms = (ArrayList<TherapyProgram>) query.list();
        return therapyPrograms;
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        // Get the last user ID from the database
        String lastId = session.createQuery("SELECT tp.id FROM TherapyProgram tp ORDER BY tp.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();

        if (lastId != null) {
            int numericPart = Integer.parseInt(lastId.split("-")[1]) + 1;
            return String.format("TP00-%03d", numericPart);
        } else {
            return "TP00-001"; // First user ID
        }
    }

    @Override
    public TherapyProgram findById(String id) {
        Session session = factoryConfiguration.getSession();
        TherapyProgram program = session.get(TherapyProgram.class, id);
        session.close();
        return program;
    }

}