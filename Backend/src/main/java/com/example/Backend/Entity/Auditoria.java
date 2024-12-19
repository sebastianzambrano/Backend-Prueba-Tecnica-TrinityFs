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
    @Column(name = "state")
    private Boolean state;
    @Schema(description = "Fecha de creación del dato")
    @Column(name = "fecha_creacion", nullable = false, updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;
    @Schema(description = "Fecha de actualización del dato")
    @Column(name = "fecha_modificacion", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime updatedAt;
    @Schema(description = "Usuario cración")
    @Column(name="usuario_creacion")
    private Long createdUser;
    @Schema(description = "Usuario modificación")
    @Column(name="usuario_modificacion")
    private Long updatedUser;
    @Schema(description = "Usuarios eliminación")
    @Column(name="usuario_eliminacion")
    private Long deletedUser;
    @Schema(description = "Fecha de eliminación del dato")
    @Column(name = "fecha_eliminacion", columnDefinition = "DATETIME")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.state     = Boolean.TRUE;
    }
}
