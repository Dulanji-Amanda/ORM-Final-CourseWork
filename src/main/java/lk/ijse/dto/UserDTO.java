package lk.ijse.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String role;
}
