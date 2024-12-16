package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Producto extends Auditoria{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCuenta;

    private BigDecimal saldo;

    private Boolean exentaGmf;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @ManyToOne(targetEntity = TipoCuenta.class)
    private TipoCuenta tipoCuenta;

    @NotBlank(message = "El cliente es obligatorio")
    @ManyToOne
    private Cliente cliente;

    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.LAZY, mappedBy = "productoDebito")
    private List<Transaccion> transaccionesDebito;

    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.LAZY, mappedBy = "productoCredito")
    private List<Transaccion> transaccionesCredito;

}
