package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapyHistoryTM {
    private String sessionId;
    private String therapistName;
    private String programName;
    private LocalDate Date;
}
