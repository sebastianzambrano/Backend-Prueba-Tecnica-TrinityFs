package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
