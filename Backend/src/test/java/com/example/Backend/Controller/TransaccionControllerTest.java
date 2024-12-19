package com.example.Backend.Controller;

import com.example.Backend.DTO.ApiResponseDto;
import com.example.Backend.Entity.Producto;
import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.Entity.Transaccion;
import com.example.Backend.IService.ITransaccionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransaccionControllerTest {

    @Mock
    private ITransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
    }

    @Test
    void testGetTransaccionById() throws Exception {
        // Configuración de datos de prueba
        TipoOperacion tipoOperacion = new TipoOperacion();
        tipoOperacion.setDescripcion("retiro");

        Producto producto = new Producto();
        producto.setSaldo(BigDecimal.valueOf(50000000));


        Long transaccionId = 1L;
        Transaccion mockTransaccion = new Transaccion();
        mockTransaccion.setId(transaccionId);
        mockTransaccion.setTipoOperacion(tipoOperacion);
        mockTransaccion.setValor(BigDecimal.valueOf(100000));
        mockTransaccion.setProductoDebito(producto);


        // Simular comportamiento del servicio
        when(transaccionService.findById(transaccionId)).thenReturn(mockTransaccion);

        // Llamar al método del controlador
        ResponseEntity<?> response = transaccionController.show(transaccionId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Crear la respuesta esperada con ApiResponseDto
        ApiResponseDto<Transaccion> expectedResponse = new ApiResponseDto<>("Registro encontrado", mockTransaccion, true);

        // Convertir la respuesta y validar los datos
        ApiResponseDto<Transaccion> actualResponse = (ApiResponseDto<Transaccion>) response.getBody();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getData(), actualResponse.getData());

        // Verificar que el servicio fue llamado correctamente
        verify(transaccionService, times(1)).findById(transaccionId);
    }

    @Test
    void testCreateTransaccion() throws Exception {
        // Configuración de datos de prueba
        TipoOperacion tipoOperacion = new TipoOperacion();
        tipoOperacion.setDescripcion("retiro");

        Producto producto = new Producto();
        producto.setSaldo(BigDecimal.valueOf(50000000));



        // Configuración de datos de prueba
        Long transaccionId = 1L;
        Transaccion newTransaccion = new Transaccion();
        newTransaccion.setId(transaccionId);
        newTransaccion.setTipoOperacion(tipoOperacion);
        newTransaccion.setValor(BigDecimal.valueOf(100000));
        newTransaccion.setProductoDebito(producto);

        Transaccion savedTransaccion = new Transaccion();
        savedTransaccion.setId(transaccionId);
        savedTransaccion.setTipoOperacion(tipoOperacion);
        savedTransaccion.setValor(BigDecimal.valueOf(100000));
        savedTransaccion.setProductoDebito(producto);

        // Simular comportamiento del servicio
        when(transaccionService.save(newTransaccion)).thenReturn(savedTransaccion);

        // Llamar al método del controlador
        ResponseEntity<?> response = transaccionController.save(newTransaccion);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Crear la respuesta esperada con ApiResponseDto
        ApiResponseDto<Transaccion> expectedResponse = new ApiResponseDto<>("Datos guardados", savedTransaccion, true);

        // Extraer el cuerpo como ApiResponseDto
        ApiResponseDto<Transaccion> actualResponse = (ApiResponseDto<Transaccion>) response.getBody();
        assertNotNull(actualResponse);

        // Validar contenido del ApiResponseDto
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getData(), actualResponse.getData());

        // Verificar que el servicio fue llamado correctamente
        verify(transaccionService, times(1)).save(newTransaccion);
    }

    @Test
    void testDeleteTransaccion() throws Exception {
        // Configuración de datos de prueba
        Long transaccionId = 1L;

        // Llamar al método del controlador
        ResponseEntity<?> response = transaccionController.delete(transaccionId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // o 204 si se espera No Content

        // Verificar que el servicio fue llamado correctamente
        verify(transaccionService, times(1)).delete(transaccionId);
    }
}