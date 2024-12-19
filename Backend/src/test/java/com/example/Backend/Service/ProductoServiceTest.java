package com.example.Backend.Service;

import com.example.Backend.Entity.*;
import com.example.Backend.IRepository.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    public void setUp() {
        producto = new Producto();
        producto.setNumeroCuenta("5331234567");
        producto.setSaldo(BigDecimal.valueOf(1000));
        producto.setEstado(EstadoProducto.ACTIVA);
        producto.setState(true);
    }

    @Test
    void testSaveProducto() throws Exception {
        Producto producto = new Producto();
        Cliente cliente = new Cliente();
        TipoCuenta tipoCuenta = new TipoCuenta();  // Asegúrate de inicializar el objeto TipoCuenta

        // Inicialización de tipoCuenta
        tipoCuenta.setId(1L);
        tipoCuenta.setDescripcion("Cuenta Ahorros");

        // Configuración del cliente
        cliente.setNombre("juan");
        cliente.setApellido("zambrano");
        cliente.setNumeroIdentificacion("1075297946");
        cliente.setFechaNacimiento(LocalDate.parse("20/07/1996", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        cliente.setTipoIdentificacion("cedula");

        // Asignamos tipoCuenta al producto
        producto.setTipoCuenta(tipoCuenta);  // Asegúrate de asignar el tipoCuenta al producto

        // Configuración del producto
        producto.setTipoCuenta(tipoCuenta);
        producto.setSaldo(BigDecimal.valueOf(0));
        producto.setEstado(EstadoProducto.ACTIVA);
        producto.setCliente(cliente);
        producto.setExentaGmf(true);

        // Arrange: Simulamos el comportamiento del repositorio para el método save
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act: Llamamos al servicio save
        Producto result = productoService.save(producto);

        // Assert: Verificamos que el producto no sea nulo y que los valores sean correctos
        assertNotNull(result);
        assertEquals("5331234567", result.getNumeroCuenta());
        assertEquals(EstadoProducto.ACTIVA, result.getEstado());
    }

    @Test
    void testFindSaldoByNumeroCuenta() {
        // Arrange
        when(productoRepository.findSaldoByNumeroCuenta("5331234567")).thenReturn(BigDecimal.valueOf(1000));

        // Act
        BigDecimal saldo = productoService.findSaldoByNumeroCuenta("5331234567");

        // Assert
        assertEquals(BigDecimal.valueOf(1000), saldo);
    }

    @Test
    void testActualizarSaldo() throws Exception {
        // Arrange
        when(productoRepository.actualizarSaldo(any(BigDecimal.class), eq("5331234567"))).thenReturn(1);

        // Act
        int result = productoService.actualizarSaldo(BigDecimal.valueOf(500), "5331234567");

        // Assert
        assertEquals(1, result);
    }

    @Test
    void testFindCuentaActivaByNumeroCuenta() throws Exception {
        // Arrange
        when(productoRepository.findCuentaActivaByNumeroCuenta("5331234567")).thenReturn(true);

        // Act
        Boolean result = productoService.findCuentaActivaByNumeroCuenta("5331234567");

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteProducto() throws Exception {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        doNothing().when(productoRepository).deleteById(1L);

        // Act
        productoService.delete(1L);

        // Assert
        verify(productoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProductoException() throws Exception {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> productoService.delete(1L));
        assertEquals("Producto no encontrado", exception.getMessage());
    }

    @Test
    void testActualizarSaldoInsuficiente(Transaccion transaccion) {
        // Arrange
        Producto productoDebito = new Producto();
        productoDebito.setNumeroCuenta("5331234567");
        productoDebito.setSaldo(BigDecimal.valueOf(100));
        Transaccion transaccionCredito = new Transaccion();
        transaccion.setValor(BigDecimal.valueOf(200));
        transaccion.setProductoDebito(productoDebito);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> productoService.validacionOperacion(transaccion));
        assertEquals("fondo insuficiente para retiro", exception.getMessage());
    }

    @Test
    void testActualizarSaldoCorrecto() throws Exception {
        // Arrange
        Producto productoCredito = new Producto();
        productoCredito.setNumeroCuenta("5339876543");
        productoCredito.setSaldo(BigDecimal.valueOf(1000));

        TipoOperacion tipoOperacion = new TipoOperacion();
        tipoOperacion.setDescripcion("retiro");

        Transaccion transaccion = new Transaccion();
        transaccion.setValor(BigDecimal.valueOf(200));
        transaccion.setProductoCredito(productoCredito);
        transaccion.setTipoOperacion(tipoOperacion);

        // Act
        productoService.actualizarSaldo(transaccion);

        // Assert
        verify(productoRepository, times(1)).actualizarSaldo(eq(BigDecimal.valueOf(1200)), eq("5339876543"));
    }
}
