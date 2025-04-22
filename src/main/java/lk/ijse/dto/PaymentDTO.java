package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class PaymentDTO {
    private String paymentId;
    private double amount;
    private LocalDate paymentDate;
    private String status;
    private String patientId;
    private String sessionId;
}
