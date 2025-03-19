package lk.ijse.entity;

import jakarta.persistence.*;
import lk.ijse.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")


public class User {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // Will be stored as a hashed password (BCrypt)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Enum (ADMIN, RECEPTIONIST, THERAPIST)

    public User(String username, String hashedPassword, Role role) {
        this.username = username;
        this.password = hashedPassword;
        this.role = (role != null) ? role : Role.RECEPTIONIST;
    }

}
