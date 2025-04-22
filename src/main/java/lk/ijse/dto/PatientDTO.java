package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientDTO {
    private String id;
    private String name;
    private String contactInfo;
    private String gender;
    private String medicalHistory;
}
