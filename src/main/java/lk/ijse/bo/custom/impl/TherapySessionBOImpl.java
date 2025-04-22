package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.TherapyProgramDAO;
import lk.ijse.dao.custom.TherapySessionDAO;
import lk.ijse.dao.custom.impl.PatientDAOImpl;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.dto.TherapySessionDTO;
import lk.ijse.entity.Therapist;
import lk.ijse.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {
    private final TherapySessionDAO sessionDAO = new TherapySessionDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();
    private final TherapistDAO therapistDAO = new TherapistDAOImpl();
    private final TherapyProgramDAO programDAO = new TherapyProgramDAOImpl();
    private final PaymentBO paymentBO = new PaymentBOImpl();

    public boolean bookSession(String sessionId, String patientId, String therapistId, String programId,
                               LocalDate date, LocalTime time) {

        // Check for conflicts
        List<TherapySession> existing = sessionDAO.findByTherapistAndTime(therapistId, date, time);
        if (!existing.isEmpty()) {
            System.out.println("Therapist is not available at this time.");
            return false;
        }

        TherapySession session = new TherapySession();
        session.setSessionId(sessionId);
        session.setSessionDate(date);
        session.setSessionTime(time);
        session.setStatus("BOOKED");
        session.setPatient(patientDAO.findById(patientId));
        session.setTherapist(therapistDAO.findById(therapistId));
        session.setTherapyProgram(programDAO.findById(programId));

        boolean sessionSaved = sessionDAO.save(session);
        if (!sessionSaved) {
//            System.out.println("Failed to save therapy session.");
            return false;
        }

//         Optional: Set availability manually if you want to reflect it elsewhere
        Therapist therapist = session.getTherapist();
        therapist.setAvailability("BUSY");
        therapistDAO.update(therapist); // Ensure update() exists in TherapistDAO

//        System.out.println("Session booked successfully.");
        return true;
    }

    public boolean rescheduleSession(String sessionId, LocalDate newDate, LocalTime newTime) {
        TherapySession session = sessionDAO.findById(sessionId);

        List<TherapySession> conflict = sessionDAO.findByTherapistAndTime(
                session.getTherapist().getTherapistID(), newDate, newTime);

        if (!conflict.isEmpty()) {
            System.out.println("Therapist not available at new time.");
            return false;
        }

        session.setSessionDate(newDate);
        session.setSessionTime(newTime);
        session.setStatus("RESCHEDULED");

        sessionDAO.save(session);
        return true;
    }

    public boolean cancelSession(String sessionId) {
        TherapySession session = sessionDAO.findById(sessionId);
        session.setStatus("CANCELLED");
        sessionDAO.save(session);

        return true;
    }

    @Override
    public ArrayList<TherapySessionDTO> loadAllSessions() {
        ArrayList<TherapySessionDTO> sessionDTOS = new ArrayList<>();
        ArrayList<TherapySession> sessions = (ArrayList<TherapySession>) sessionDAO.getAll();

        for (TherapySession session : sessions) {
            sessionDTOS.add(
                    new TherapySessionDTO(
                            session.getSessionId(),
                            session.getSessionDate(),
                            session.getSessionTime(),
                            session.getStatus(),
                            session.getPatient().getId(),
                            session.getTherapyProgram().getProgramId(),
                            session.getTherapist().getTherapistID()
                    ));

        }
        return sessionDTOS;
    }

    @Override
    public TherapySessionDTO getSessionByPatientName(String name) {
        TherapySession ts = sessionDAO.getSessionByPatientName(name);

        if (ts != null) {
            return new TherapySessionDTO(
                    ts.getSessionId(),
                    ts.getSessionDate(),
                    ts.getSessionTime(),
                    ts.getStatus(),
                    ts.getPatient().getId(),
                    ts.getTherapyProgram().getProgramId(),
                    ts.getTherapist().getTherapistID()
            );
        } else {
            return null;
        }
    }

    @Override
    public boolean bookSessionWithPayment(String sessionId, String patientId, String therapistId, String programId, LocalDate sessionDate, LocalTime sessionTime, String paymentId) {
        return false;
    }

}