package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.custom.PatientDAO;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dao.custom.TherapySessionDAO;
import lk.ijse.dao.custom.impl.PatientDAOImpl;
import lk.ijse.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Patient;
import lk.ijse.entity.Payment;
import lk.ijse.entity.TherapySession;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    private final PaymentDAO paymentDAO = new PaymentDAOImpl();
    private final TherapySessionDAO therapySessionDAO = new TherapySessionDAOImpl();
    private final PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) {
        Patient patient = patientDAO.findById(paymentDTO.getPatientId());
        TherapySession session = therapySessionDAO.findById(paymentDTO.getSessionId());

        return paymentDAO.save(new Payment(
                paymentDTO.getPaymentId(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus(),
                patient,
                session
        ));
    }

    @Override
    public String getNextPaymentID() {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDTO> loadAllPayments() {
        ArrayList<PaymentDTO> paymentDTOList = new ArrayList<>();
        try {
            List<Payment> paymentList = paymentDAO.getAll();

            for (Payment payment : paymentList) {
                String sessionId = (payment.getSession() != null) ? payment.getSession().getSessionId() : "N/A";

                PaymentDTO dto = new PaymentDTO(
                        payment.getPaymentId(),
                        payment.getAmount(),
                        payment.getPaymentDate(),
                        payment.getStatus(),
                        payment.getPatient().getId(),
                        sessionId
                );

                paymentDTOList.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentDTOList;
    }

    @Override
    public long getCompletedPaymentCount() {
        try {
            return paymentDAO.getAll().stream()
                    .filter(payment -> "completed".equalsIgnoreCase(payment.getStatus()))
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public long getPendingPaymentCount() {
        try {
            return paymentDAO.getAll().stream()
                    .filter(payment -> "pending".equalsIgnoreCase(payment.getStatus()))
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }    }

}