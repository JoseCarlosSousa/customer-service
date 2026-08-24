package pt.kkosmico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @jakarta.validation.constraints.Email
    @jakarta.validation.constraints.NotBlank
    private String email;
    private String password;
    private String role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = java.time.LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private boolean active = true;
}