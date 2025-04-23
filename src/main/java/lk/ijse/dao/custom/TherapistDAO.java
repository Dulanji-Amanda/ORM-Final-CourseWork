package lk.ijse.dao.custom;

import lk.ijse.entity.Therapist;

public interface TherapistDAO extends CrudDAO<Therapist> {
    Therapist getById(String therapistId);

}