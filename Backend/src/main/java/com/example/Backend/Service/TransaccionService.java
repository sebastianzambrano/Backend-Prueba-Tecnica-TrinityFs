package com.example.Backend.Service;

import com.example.Backend.Entity.Transaccion;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IRepository.ITransaccionRepository;
import com.example.Backend.IService.IProductoService;
import com.example.Backend.IService.ITransaccionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransaccionService extends BaseService<Transaccion> implements ITransaccionService {
    @Autowired
    private ITransaccionRepository repository;

    @Autowired
    private IProductoService productoService;

    @Override
    protected IBaseRepository<Transaccion, Long> getRepository() {
        return repository;
    }

    @Transactional
    @Override
    public Transaccion save(Transaccion transaccion) throws Exception{
        operacionProducto(transaccion);
        return getRepository().save(transaccion);
    }

    @Transactional
    private void operacionProducto(Transaccion transaccion) throws Exception {

        cuentaRecibida(transaccion);
        operacionEntreMismaCuenta(transaccion);
        productoService.actualizarSaldo(transaccion);
    }
    void operacionEntreMismaCuenta(Transaccion transaccion)throws Exception {
        System.out.println("se valida cuentas para transferencia");
        if (transaccion.getTipoOperacion().getId() == 3 && transaccion.getProductoDebito().getNumeroCuenta().equals(transaccion.getProductoCredito().getNumeroCuenta())) {
            throw new Exception("No se puede realizar operaciones entre la misma cuenta");
        }
    }
    void cuentaRecibida(Transaccion transaccion)  throws Exception {

        Long tipoOperacion = transaccion.getTipoOperacion().getId();

        if (tipoOperacion == 2 && (transaccion.getProductoCredito() == null || transaccion.getProductoDebito() != null)) {
            throw new Exception("Producto crédito es necesario para esta operación");
        }

        if (tipoOperacion == 2 && (transaccion.getProductoCredito() != null && transaccion.getProductoDebito() == null)) {
            throw new Exception("ElProducto debito no se requiere");
        }

        if (tipoOperacion == 1  && transaccion.getProductoDebito() == null) {
            throw new Exception("Producto débito es necesario para esta operación");
        }

        if (tipoOperacion == 3 && (transaccion.getProductoDebito() == null || transaccion.getProductoCredito() == null)) {
            throw new Exception("Se requiere el producto debito y producto crédito");
        }

    }
}

