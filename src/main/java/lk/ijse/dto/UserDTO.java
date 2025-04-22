package lk.ijse.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
}
