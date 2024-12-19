package com.example.Backend.IService;

import com.example.Backend.Entity.Producto;
import com.example.Backend.Entity.Transaccion;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface IProductoService extends IBaseService<Producto> {

    List<String> findNumeroCuentaByTipoCuentaId(@Param("tipoCuentaId") Long tipoCuentaId);

    List<Long> findAllIdsByTipoCuentaId();

    BigDecimal findSaldoByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta) throws Exception;

    Long contarCuentasByClienteId(@Param("id") Long id);

    int actualizarSaldo(@Param("saldo") BigDecimal saldo, @Param("numeroCuenta") String numeroCuenta);

    Boolean findCuentaActivaByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta) throws Exception;

    void actualizarSaldo(Transaccion transaccion) throws Exception;

    void validarTipoCuenta(Producto producto)throws Exception;

    void validarProductoRelacionPersona(Producto producto)throws Exception;

    void asignacionNumeroCuenta(Producto producto)throws Exception;

    boolean existeNumeroCuenta(String numeroCuentaGenerado, Producto producto);
    void validarProductoEliminacion(Producto producto) throws Exception;

    void validacionOperacion(Transaccion transaccion) throws Exception;
}
