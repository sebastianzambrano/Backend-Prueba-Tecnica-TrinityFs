package com.example.Backend.Service;

import com.example.Backend.Entity.Transaccion;
import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.Entity.Producto;
import com.example.Backend.IRepository.ITransaccionRepository;
import com.example.Backend.IService.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransaccionServiceTest {

    @Mock
    private ITransaccionRepository transaccionRepository;

    @Mock
    private IProductoService productoService;

    @InjectMocks
    private TransaccionService transaccionService;

    private Transaccion transaccion;
    private Producto productoDebito;
    private Producto productoCredito;
    private TipoOperacion tipoOperacion;

    @BeforeEach
    public void setUp() {
        // Inicializamos los objetos necesarios para las pruebas
        productoDebito = new Producto();
        productoDebito.setNumeroCuenta("12345");

        productoCredito = new Producto();
        productoCredito.setNumeroCuenta("67890");

        tipoOperacion = new TipoOperacion();
        tipoOperacion.setId(1L);

        transaccion = new Transaccion();
        transaccion.setTipoOperacion(tipoOperacion);
        transaccion.setProductoDebito(productoDebito);
        transaccion.setProductoCredito(productoCredito);
    }

    @Test
    void testSaveWithValidTransaccion() throws Exception {
        // Arrange: Simulamos el comportamiento del repositorio y del servicio de producto
        when(transaccionRepository.save(transaccion)).thenReturn(transaccion);
        doNothing().when(productoService).actualizarSaldo(transaccion);

        // Act: Llamamos al método save del servicio
        Transaccion result = transaccionService.save(transaccion);

        // Assert: Verificamos que la transacción haya sido guardada correctamente
        assertNotNull(result);
        assertEquals("12345", result.getProductoDebito().getNumeroCuenta());
        assertEquals("67890", result.getProductoCredito().getNumeroCuenta());
    }

    @Test
    void testSaveWithInvalidOperacionEntreMismaCuenta() {
        // Arrange: Simulamos una transacción entre la misma cuenta
        tipoOperacion.setId(3L);  // Tipo de operación 3 que corresponde a una transferencia
        transaccion.setProductoDebito(productoDebito);
        transaccion.setProductoCredito(productoDebito);  // Misma cuenta para débito y crédito

        // Act & Assert: Esperamos que se lance una excepción
        assertThrows(Exception.class, () -> transaccionService.save(transaccion));
    }

    @Test
    void testSaveWithMissingProductoCreditoForOperacionTipo2() {
        // Arrange: Tipo de operación 2 requiere un Producto Crédito
        tipoOperacion.setId(2L);
        transaccion.setProductoCredito(null);  // Falta Producto Crédito
        transaccion.setProductoDebito(productoDebito);

        // Act & Assert: Esperamos que se lance una excepción
        assertThrows(Exception.class, () -> transaccionService.save(transaccion));
    }

    @Test
    void testSaveWithMissingProductoDebitoForOperacionTipo1() {
        // Arrange: Tipo de operación 1 requiere un Producto Débito
        tipoOperacion.setId(1L);
        transaccion.setProductoDebito(null);  // Falta Producto Débito
        transaccion.setProductoCredito(productoCredito);

        // Act & Assert: Esperamos que se lance una excepción
        assertThrows(Exception.class, () -> transaccionService.save(transaccion));
    }

    @Test
    void testSaveWithMissingBothProductosForOperacionTipo3() {
        // Arrange: Tipo de operación 3 requiere Producto Débito y Crédito
        tipoOperacion.setId(3L);
        transaccion.setProductoDebito(null);  // Falta Producto Débito
        transaccion.setProductoCredito(null);  // Falta Producto Crédito

        // Act & Assert: Esperamos que se lance una excepción
        assertThrows(Exception.class, () -> transaccionService.save(transaccion));
    }
}