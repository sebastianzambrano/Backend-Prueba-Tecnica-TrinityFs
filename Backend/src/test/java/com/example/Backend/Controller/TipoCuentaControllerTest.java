package com.example.Backend.Controller;

import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.IService.ITipoCuentaService;
import com.example.Backend.DTO.ApiResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TipoCuentaControllerTest {

    @Mock
    private ITipoCuentaService tipoCuentaService;

    @InjectMocks
    private TipoCuentaController tipoCuentaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
    }

    @Test
    void testGetTipoCuentaById() throws Exception {
        // Configuración de datos de prueba
        Long tipoCuentaId = 1L;
        TipoCuenta mockTipoCuenta = new TipoCuenta();
        mockTipoCuenta.setId(tipoCuentaId);
        mockTipoCuenta.setDescripcion("Ahorros");

        // Crear la respuesta esperada con ApiResponseDto usando TipoCuenta como tipo
        ApiResponseDto<TipoCuenta> mockResponse = new ApiResponseDto<>();

        // Simular comportamiento del servicio
        when(tipoCuentaService.findById(tipoCuentaId)).thenReturn(mockTipoCuenta);

        // Llamar al método del controlador
        ResponseEntity<?> response = tipoCuentaController.show(tipoCuentaId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Convertir la respuesta y validar los datos
        ApiResponseDto<TipoCuenta> actualResponse = (ApiResponseDto<TipoCuenta>) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("Registro encontrado", actualResponse.getMessage());
        assertEquals(mockTipoCuenta, actualResponse.getData());

        // Verificar que el servicio fue llamado correctamente
        verify(tipoCuentaService, times(1)).findById(tipoCuentaId);
    }

    @Test
    void testCreateTipoCuenta() throws Exception {
        // Configuración de datos de prueba
        Long tipoCuentaId = 1L;
        TipoCuenta newTipoCuenta = new TipoCuenta();
        newTipoCuenta.setDescripcion("Ahorros");

        TipoCuenta savedTipoCuenta = new TipoCuenta();
        savedTipoCuenta.setId(tipoCuentaId);
        savedTipoCuenta.setDescripcion("Ahorros");

        // Simular comportamiento del servicio
        when(tipoCuentaService.save(newTipoCuenta)).thenReturn(savedTipoCuenta);

        // Llamar al método del controlador
        ResponseEntity<?> response = tipoCuentaController.save(newTipoCuenta);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Extraer el cuerpo como ApiResponseDto
        ApiResponseDto<TipoCuenta> actualResponse = (ApiResponseDto<TipoCuenta>) response.getBody();
        assertNotNull(actualResponse);

        // Validar contenido del ApiResponseDto
        assertEquals("Datos guardados", actualResponse.getMessage()); // Ajusta este mensaje si es necesario
        assertEquals(savedTipoCuenta, actualResponse.getData()); // Verifica que el tipo de cuenta contenido sea el esperado

        // Verificar que el servicio fue llamado correctamente
        verify(tipoCuentaService, times(1)).save(newTipoCuenta);
    }

    @Test
    void testDeleteTipoCuenta() throws Exception {
        // Configuración de datos de prueba
        Long tipoCuentaId = 1L;

        // Llamar al método del controlador
        ResponseEntity<?> response = tipoCuentaController.delete(tipoCuentaId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue()); // o 204 si se espera No Content

        // Verificar que el servicio fue llamado correctamente
        verify(tipoCuentaService, times(1)).delete(tipoCuentaId);
    }
}