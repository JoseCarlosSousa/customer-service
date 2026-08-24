package pt.kkosmico.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active ;
}