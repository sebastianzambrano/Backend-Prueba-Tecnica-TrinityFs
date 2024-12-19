package com.example.Backend.Service;

import com.example.Backend.Entity.EstadoProducto;
import com.example.Backend.Entity.Producto;
import com.example.Backend.Entity.Transaccion;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IRepository.IProductoRepository;
import com.example.Backend.IService.IProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductoService extends BaseService<Producto> implements IProductoService {
    @Autowired
    private IProductoRepository repository;
    @Override
    protected IBaseRepository<Producto, Long> getRepository() {return repository;}
    @Override
    public List<String> findNumeroCuentaByTipoCuentaId(Long tipoCuentaId) {
        return repository.findNumeroCuentaByTipoCuentaId(tipoCuentaId);
    }
    @Override
    public List<Long> findAllIdsByTipoCuentaId() {
        return repository.findAllIdsByTipoCuentaId();
    }

    @Override
    public BigDecimal findSaldoByNumeroCuenta(String numeroCuenta) {
        return repository.findSaldoByNumeroCuenta(numeroCuenta);
    }

    @Override
    public Long contarCuentasByClienteId(Long id) {
        return repository.contarCuentasByClienteId(id);
    }

    @Transactional
    @Override
    public int actualizarSaldo(BigDecimal saldo, String numeroCuenta) {
        return repository.actualizarSaldo(saldo, numeroCuenta);
    }

    @Override
    public Boolean findCuentaActivaByNumeroCuenta(String numeroCuenta) throws Exception {
        Boolean estadoCuenta = repository.findCuentaActivaByNumeroCuenta(numeroCuenta);
        if (estadoCuenta == null || !estadoCuenta) {
            throw new Exception("La cuenta está cancelada o no existe.");
        }
        return estadoCuenta;
    }

    @Transactional
    public Producto save(Producto producto) throws Exception {
        validarProductoRelacionPersona(producto);
        asignacionNumeroCuenta(producto);
        validarTipoCuenta(producto);
        producto.setState(true);
        producto.setEstado(EstadoProducto.ACTIVA);
        return super.save(producto);
    }
    @Transactional
    @Override
    public void delete(Long id)throws Exception{
        System.out.println("ingreso a al operacion de eliminación");
        Optional<Producto> op = repository.findById(id); // Usa el repositorio específico

        if (op.isEmpty() || op.get().getState() == false) {
            throw new Exception("Producto no encontrado");
        }

        Producto producto = op.get();
        validarProductoEliminacion(producto);
        producto.setDeletedAt(LocalDateTime.now());
        producto.setEstado(EstadoProducto.CANCELADA);
        producto.setState(false);
        super.delete(id);
    }

    @Override
    public void actualizarSaldo(Transaccion transaccion) throws Exception {
        //Extraer tipo operacion
        Long tipoOperacion = transaccion.getTipoOperacion().getId();

        //Extraer valor operacion
        BigDecimal valorOperacion = transaccion.getValor();

        BigDecimal nuevoSaldo;

        if(tipoOperacion==1 || tipoOperacion==3){
            String numeroCuentaDebito = transaccion.getProductoDebito().getNumeroCuenta();
            BigDecimal saldoCuentaProductoDebito = findSaldoByNumeroCuenta(numeroCuentaDebito);

            validacionOperacion(transaccion);

            nuevoSaldo = saldoCuentaProductoDebito.subtract(valorOperacion);
            actualizarSaldo(nuevoSaldo,numeroCuentaDebito);
        }
        //consignacion dinero (consignacion o transferencia)
        if(tipoOperacion==2 || tipoOperacion==3){
            String numeroCuentaCredito = transaccion.getProductoCredito().getNumeroCuenta();


            if (!findCuentaActivaByNumeroCuenta(numeroCuentaCredito)) {
                throw new Exception("Cuenta cancelada o inactiva.");
            }


            BigDecimal saldoCuentaProductoCredito = findSaldoByNumeroCuenta(numeroCuentaCredito);
            nuevoSaldo = saldoCuentaProductoCredito.add(valorOperacion);
            actualizarSaldo(nuevoSaldo, numeroCuentaCredito);
        }
    }

    @Override
        public void validacionOperacion(Transaccion transaccion) throws Exception{

        Long tipoOperacion = transaccion.getTipoOperacion().getId();
        BigDecimal valorOperacion = transaccion.getValor();
        BigDecimal saldoCuentaProductoDebito = findSaldoByNumeroCuenta(transaccion.getProductoDebito().getNumeroCuenta());

        if((tipoOperacion == 1 || tipoOperacion == 3) && saldoCuentaProductoDebito.compareTo(valorOperacion) < 0 ){
            throw new Exception("fondo insuficiente para retiro");
        }
    }

    @Override
    public void validarTipoCuenta(Producto producto) throws Exception {
        List<Long> tiposCuentas = repository.findAllIdsByTipoCuentaId();
        if (!tiposCuentas.contains(producto.getTipoCuenta().getId())){
            throw new Exception("Tipo de cuenta erroneo");
        }
    }
    @Override
    public void validarProductoRelacionPersona(Producto producto) throws Exception {
        if(producto.getCliente() == null){
            throw new Exception("La cuenta debe pertenecer a un cliente");
        }
    }
    @Override
    public void validarProductoEliminacion(Producto producto) throws Exception {

        if(producto.getSaldo().compareTo(BigDecimal.ZERO) > 0){
            throw new Exception("El saldo de la cuenta debe estar en 0");
        }
    }
    @Override
    public void asignacionNumeroCuenta(Producto producto) throws Exception {
        Random random = new Random();
        long numeroCuenta;
        String numeroCuentaGenerado;
        do {
            numeroCuenta = 10000000L + (long) (random.nextDouble() * 90000000L);
            if (producto.getTipoCuenta().getId() == 1) {
                numeroCuentaGenerado = "53" + Long.toString(numeroCuenta);
            }
            else if (producto.getTipoCuenta().getId() == 2) {
                numeroCuentaGenerado = "33" + Long.toString(numeroCuenta);
            }
            else {
               throw new Exception("Tipo de cuenta no válido");
            }
        }
        while (existeNumeroCuenta(numeroCuentaGenerado, producto));
        producto.setNumeroCuenta(numeroCuentaGenerado);
    }
    @Override
    public boolean existeNumeroCuenta(String numeroCuentaGenerado, Producto producto) {
        List<String> numeroCuentasExistentes = repository.findNumeroCuentaByTipoCuentaId(producto.getTipoCuenta().getId());
        return numeroCuentasExistentes.contains(numeroCuentaGenerado);
    }


}

