package com.example.Backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto extends Auditoria{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Debe tener un numero de cuenta")
    private String numeroCuenta;
    @NotNull(message = "Debe iniciar con saldo 0")
    private BigDecimal saldo;
    @NotNull(message = "Debe indicar si la cuenta esta exenta")
    private Boolean exentaGmf;
    private EstadoProducto estado;
    @NotNull(message = "El tipo de cuenta es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cuenta_id",nullable = false)
    private TipoCuenta tipoCuenta;

    @NotNull(message = "El cliente es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.LAZY, mappedBy = "productoDebito")
    private List<Transaccion> transaccionesDebito;

    @OneToMany(targetEntity = Transaccion.class, fetch = FetchType.LAZY, mappedBy = "productoCredito")
    private List<Transaccion> transaccionesCredito;

}
