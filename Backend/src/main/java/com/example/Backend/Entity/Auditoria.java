package com.example.Backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@MappedSuperclass
public abstract class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "state", nullable = false)
    private Boolean state;
    @Schema(description = "Fecha de creación del dato", example = "")
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
    @Schema(description = "Fecha de actualización del dato", example = "")
    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;
    @Schema(description = "Usuario cración", example = "")
    @Column(name="created_user", nullable = true)
    private Long createdUser;
    @Schema(description = "Usuario modificación", example = "")
    @Column(name="updated_user", nullable = true)
    private Long updatedUser;
    @Schema(description = "Usuarios eliminación", example = "")
    @Column(name="deleted_user", nullable = true)
    private Long deletedUser;
    @Schema(description = "Fecha de eliminación del dato", example = "")
    @Column(name = "deleted_at", nullable = true, columnDefinition = "DATETIME")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.state     = Boolean.TRUE;
    }
}
