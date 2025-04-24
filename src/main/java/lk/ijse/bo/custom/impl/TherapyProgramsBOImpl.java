package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.dao.custom.TherapistDAO;
import lk.ijse.dao.custom.TherapyProgramDAO;
import lk.ijse.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.dto.TherapyProgramDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapyProgramsBOImpl implements TherapyProgramsBO {

    TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
    TherapistDAO therapistDAO = new TherapistDAOImpl();

    @Override
    public ArrayList<TherapyProgramDTO> loadAllTherapyPrograms() throws SQLException, ClassNotFoundException {

        ArrayList<TherapyProgramDTO> therapyProgramDTOS = new ArrayList<>();
        ArrayList<TherapyProgram> therapyPrograms = (ArrayList<TherapyProgram>) therapyProgramDAO.getAll();

        for (TherapyProgram therapyProgram : therapyPrograms) {
            therapyProgramDTOS.add(
                    new TherapyProgramDTO(
                            therapyProgram.getProgramId(),
                            therapyProgram.getProgramName(),
                            therapyProgram.getDuration(),
                            therapyProgram.getFee()
                    )
            );
        }
        return therapyProgramDTOS;
    }

    @Override
    public String getNextTherapyProgramId() {
        return therapyProgramDAO.getNextId();
    }

    @Override
    public TherapyProgram findById(String programId) {
        TherapyProgram therapyProgram = therapyProgramDAO.findById(programId);
        if (therapyProgram != null) {
            return new TherapyProgram(
                    therapyProgram.getProgramId(),
                    therapyProgram.getProgramName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getFee(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
        return null;    }

    @Override
    public boolean saveTherapyPrograms(TherapyProgramDTO therapyProgramDTO) {
        // Retrieve the Therapist entity using the therapist ID
//        Therapist therapist = therapistDAO.getById(therapyProgramDTO.getTherapistId());

//        if (therapist == null) {
//            throw new IllegalArgumentException("Therapist not found for ID: " + therapyProgramDTO.getTherapistId());
//        }

        return therapyProgramDAO.save(
                new TherapyProgram(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee(),
                        new ArrayList<>(),
                        new ArrayList<>()
//                        therapist // Fix: Pass Therapist entity, not String
                )
        );
    }

    @Override
    public boolean updateTherapyPrograms(TherapyProgramDTO therapyProgramDTO) {
        // Retrieve the Therapist entity using the therapist ID
//        Therapist therapist = therapistDAO.getById(therapyProgramDTO.getTherapistId());

//        if (therapist == null) {
//            throw new IllegalArgumentException("Therapist not found for ID: " + therapyProgramDTO.getTherapistId());
//        }

        return therapyProgramDAO.update(
                new TherapyProgram(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee(),
                        new ArrayList<>(),
                        new ArrayList<>()
//                        therapist // Fix: Pass Therapist entity, not String
                )
        );
    }

    @Override
    public boolean deleteTherapyPrograms(String id) throws Exception {
        return therapyProgramDAO.deleteByPK(id);
    }

}