package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.entity.Patient;

import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    @Override
    public boolean save(Patient entity) {
        return false;
    }

    @Override
    public boolean update(Patient entity) {
        return false;
    }

    @Override
    public boolean deleteByPK(String id) throws Exception {
        return false;
    }

    @Override
    public List<Patient> getAll() {
        return List.of();
    }

    @Override
    public String getNextId() {
        return "";
    }
}
