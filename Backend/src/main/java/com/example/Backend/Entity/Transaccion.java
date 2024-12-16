package com.example.Backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaccion extends Auditoria{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(targetEntity = TipoOperacion.class)
    private TipoOperacion tipoOperacion;

    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "producto_debito_id", nullable = false)
    private Producto productoDebito;

    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "producto_credito_id", nullable = false)
    private Producto productoCredito;

}
