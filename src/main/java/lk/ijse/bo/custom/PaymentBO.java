package lk.ijse.bo.custom;

import lk.ijse.dto.PaymentDTO;

import java.util.ArrayList;

public interface PaymentBO {

    boolean savePayment(PaymentDTO paymentDTO);
    String getNextPaymentID();
    ArrayList<PaymentDTO> loadAllPayments();

    long getCompletedPaymentCount();

    long getPendingPaymentCount();
}