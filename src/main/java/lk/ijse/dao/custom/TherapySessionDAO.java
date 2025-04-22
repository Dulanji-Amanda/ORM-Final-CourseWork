package lk.ijse.dao.custom;

import lk.ijse.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {
    TherapySession findById(String sessionId) ;
    List<TherapySession> findByTherapistAndTime(String therapistId, LocalDate date, LocalTime time) ;
    TherapySession getSessionByPatientName(String name) ;

}