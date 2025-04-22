package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapist_program")
public class TherapistProgram {
    @EmbeddedId
    private TherapistProgramID id;

    @ManyToOne
    @MapsId("therapistID")
    @JoinColumn(
            name = "therapist_id",
            referencedColumnName = "therapist_id",
            foreignKey = @ForeignKey(
                    name = "FK_therapist_program_therapist",
                    foreignKeyDefinition = "FOREIGN KEY (therapist_id) REFERENCES therapist (therapist_id) ON DELETE CASCADE"
            )
    )
    private Therapist therapist;

    @ManyToOne
    @MapsId("programId")
    @JoinColumn(
            name = "program_id",
            referencedColumnName = "program_id",
            foreignKey = @ForeignKey(
                    name = "FK_therapist_program_program",
                    foreignKeyDefinition = "FOREIGN KEY (program_id) REFERENCES therapy_program (program_id) ON DELETE CASCADE"
            )
    )
    private TherapyProgram therapyProgram;
}