package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TipoCuenta extends Auditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String descripcion;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.LAZY, mappedBy = "tipoCuenta")
    private List<Producto> productos;
}
