package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    private String id;
    private String name;
    private String contactInfo;
    private String gender;
    private String medicalHistory;

    @OneToMany(mappedBy = "patient" , cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Payment> payments;

}