package com.example.Backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Cliente extends Auditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String correoElectronico;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaNacimiento;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Producto> productos;

}
