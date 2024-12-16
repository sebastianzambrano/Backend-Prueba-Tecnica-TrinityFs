package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TipoOperacion extends Auditoria{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank(message = "El tipo de operacion es obligatorio")
    private String descripcion;

    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.LAZY, mappedBy = "tipoOperacion")
    private List<Transaccion> transacciones;
}
