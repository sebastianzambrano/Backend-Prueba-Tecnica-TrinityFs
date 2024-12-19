package com.example.Backend.IRepository;

import com.example.Backend.Entity.Producto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IProductoRepository extends IBaseRepository<Producto, Long> {

    @Query("SELECT p.numeroCuenta FROM Producto p WHERE p.tipoCuenta.id = :tipoCuentaId")
    List<String> findNumeroCuentaByTipoCuentaId(@Param("tipoCuentaId") Long tipoCuentaId);

    @Query("SELECT p.id FROM TipoCuenta p ")
    List<Long> findAllIdsByTipoCuentaId();

    @Query("SELECT p.saldo FROM Producto p WHERE p.numeroCuenta = :numeroCuenta")
    BigDecimal findSaldoByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query("SELECT p.state FROM Producto p WHERE p.numeroCuenta = :numeroCuenta")
    Boolean findCuentaActivaByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query("SELECT COUNT(p) FROM Producto p INNER JOIN p.cliente c WHERE c.id = :id AND p.state = true")
    Long contarCuentasByClienteId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.saldo = :saldo, p.updatedAt = CURRENT_TIMESTAMP WHERE p.numeroCuenta = :numeroCuenta")
    int actualizarSaldo(@Param("saldo") BigDecimal saldo, @Param("numeroCuenta") String numeroCuenta);
}
