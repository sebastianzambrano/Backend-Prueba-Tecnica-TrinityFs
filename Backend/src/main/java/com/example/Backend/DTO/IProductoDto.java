package com.example.Backend.DTO;

import com.example.Backend.Entity.EstadoProducto;

import java.math.BigDecimal;

public interface IProductoDto {
    Long getId();
    BigDecimal getSaldo();
    Boolean getExentaGmf();
    EstadoProducto getEstado();

}
