package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
@Data
@Entity
public class TipoCuenta extends Auditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private String descripcion;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.LAZY, mappedBy = "tipoCuenta")
    @NotBlank(message = "El producto es obligatorio")
    private List<Producto> productos;
}
