package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaccion extends Auditoria{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull(message = "El valor no puede ser nulo")
    private BigDecimal valor;
    @ManyToOne(targetEntity = TipoOperacion.class)
    private TipoOperacion tipoOperacion;
    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "producto_debito_id")
    private Producto productoDebito;
    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "producto_credito_id")
    private Producto productoCredito;

}
