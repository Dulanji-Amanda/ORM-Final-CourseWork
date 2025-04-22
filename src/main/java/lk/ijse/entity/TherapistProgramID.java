package lk.ijse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TherapistProgramID implements Serializable {
    @Column(name = "therapist_id", length = 50)
    private String therapistID;

    @Column(name = "program_id", length = 50)
    private String programId;
}