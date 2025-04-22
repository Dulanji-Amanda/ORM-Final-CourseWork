package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentTM {
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String status;
    private String patientId;
    private String sessionId;

}