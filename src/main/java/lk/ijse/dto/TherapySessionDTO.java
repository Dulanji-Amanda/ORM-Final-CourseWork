package lk.ijse.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class TherapySessionDTO {
    private String sessionId;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String status;
    private String patientId;
    private String programId;
    private String therapistId;
}
