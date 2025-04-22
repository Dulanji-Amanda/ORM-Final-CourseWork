package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapistDTO {
    private String therapistID;
    private String therapistName;
    private String specialization;
    private String availability;
}
