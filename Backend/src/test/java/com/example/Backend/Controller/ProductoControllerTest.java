package com.example.Backend.Controller;

import com.example.Backend.DTO.ApiResponseDto;
import com.example.Backend.Entity.Cliente;
import com.example.Backend.Entity.EstadoProducto;
import com.example.Backend.Entity.Producto;
import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.IService.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoControllerTest {

    @Mock
    private IProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
    }

    @Test
    void testGetProductoById() throws Exception {
        // Configuración de datos de prueba
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setDescripcion("ahorros");

        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos López");

        Long productoId = 1L;
        Producto mockProducto = new Producto();
        mockProducto.setId(productoId);
        mockProducto.setSaldo(BigDecimal.valueOf(0L));
        mockProducto.setExentaGmf(true);
        mockProducto.setEstado(EstadoProducto.ACTIVA);
        mockProducto.setTipoCuenta(tipoCuenta);
        mockProducto.setCliente(cliente);

        ApiResponseDto mockResponse = new ApiResponseDto();

        // Simular comportamiento del servicio
        when(productoService.findById(productoId)).thenReturn(mockProducto);

        // Llamar al método del controlador
        ResponseEntity<?> response = productoController.show(productoId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ApiResponseDto actualResponse = (ApiResponseDto) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("Registro encontrado", actualResponse.getMessage());
        assertEquals(mockProducto, actualResponse.getData());

        // Verificar que el servicio fue llamado correctamente
        verify(productoService, times(1)).findById(productoId);
    }

    @Test
    void testCreateProducto() throws Exception {
        // Configuración de datos de prueba
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setDescripcion("ahorros");

        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos López");

        Long productoId = 1L;
        Producto newProducto = new Producto();
        newProducto.setSaldo(BigDecimal.valueOf(0L));
        newProducto.setExentaGmf(true);
        newProducto.setEstado(EstadoProducto.ACTIVA);
        newProducto.setTipoCuenta(tipoCuenta);
        newProducto.setCliente(cliente);

        Producto savedProducto = new Producto();
        savedProducto.setId(productoId);
        newProducto.setSaldo(BigDecimal.valueOf(0L));
        newProducto.setExentaGmf(true);
        newProducto.setEstado(EstadoProducto.ACTIVA);
        newProducto.setTipoCuenta(tipoCuenta);
        newProducto.setCliente(cliente);

        // Simular comportamiento del servicio
        when(productoService.save(newProducto)).thenReturn(savedProducto);

        // Llamar al método del controlador
        ResponseEntity<?> response = productoController.save(newProducto);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Extraer el cuerpo como ApiResponseDto
        ApiResponseDto actualResponse = (ApiResponseDto) response.getBody();
        assertNotNull(actualResponse);

        // Validar contenido del ApiResponseDto
        assertEquals("Datos guardados", actualResponse.getMessage()); // Ajusta este mensaje si es necesario
        assertEquals(savedProducto, actualResponse.getData()); // Verifica que el producto contenido sea el esperado

        // Verificar que el servicio fue llamado correctamente
        verify(productoService, times(1)).save(newProducto);
    }

    @Test
    void testDeleteProducto() throws Exception {
        // Configuración de datos de prueba
        Long productoId = 1L;

        // Llamar al método del controlador
        ResponseEntity<?> response = productoController.delete(productoId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // o 204 si se espera No Content

        // Verificar que el servicio fue llamado correctamente
        verify(productoService, times(1)).delete(productoId);
    }
}