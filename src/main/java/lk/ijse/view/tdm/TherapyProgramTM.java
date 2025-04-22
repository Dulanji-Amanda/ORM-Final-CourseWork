package lk.ijse.view.tdm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyProgramTM {
    private String programId;
    private String programName;
    private int duration;
    private double fee;
}
