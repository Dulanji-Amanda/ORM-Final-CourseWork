package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    private String paymentId;

    private double amount;
    private LocalDate paymentDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "session_id")
    private TherapySession session;

}
