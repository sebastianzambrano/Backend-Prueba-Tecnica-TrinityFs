package com.example.Backend.Controller;

import com.example.Backend.DTO.ApiResponseDto;
import com.example.Backend.Entity.Cliente;
import com.example.Backend.IService.IClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteControllerTest {

    @Mock
    private IClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClienteById() throws Exception{
// Configuración de datos de prueba
        Long clienteId = 1L;
        Cliente mockCliente = new Cliente();
        mockCliente.setId(clienteId);
        mockCliente.setNombre("Juan Pérez");

        ApiResponseDto mockResponse = new ApiResponseDto();

        // Simular comportamiento del servicio
        when(clienteService.findById(clienteId)).thenReturn(mockCliente);

        // Llamar al método del controlador
        ResponseEntity<?> response = clienteController.show(clienteId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        ApiResponseDto actualResponse = (ApiResponseDto) response.getBody();
        assertNotNull(actualResponse);
        assertEquals("Registro encontrado", actualResponse.getMessage());
        assertEquals(mockCliente, actualResponse.getData());

        // Verificar que el servicio fue llamado correctamente
        verify(clienteService, times(1)).findById(clienteId);
    }

    @Test
    void testCreateCliente() throws Exception{
        // Configuración de datos de prueba
        Long clienteId = 1L;
        Cliente newCliente = new Cliente();
        newCliente.setNombre("Carlos López");

        Cliente savedCliente = new Cliente();
        savedCliente.setId(clienteId);
        savedCliente.setNombre("Carlos López");

        // Simular comportamiento del servicio
        when(clienteService.save(newCliente)).thenReturn(savedCliente);

        // Llamar al método del controlador
        ResponseEntity<?> response = clienteController.save(newCliente);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Extraer el cuerpo como ApiResponseDto
        ApiResponseDto actualResponse = (ApiResponseDto) response.getBody();
        assertNotNull(actualResponse);

        // Validar contenido del ApiResponseDto
        assertEquals("Datos guardados", actualResponse.getMessage()); // Ajusta este mensaje si es necesario
        assertEquals(savedCliente, actualResponse.getData()); // Verifica que el cliente contenido sea el esperado

        // Verificar que el servicio fue llamado correctamente
        verify(clienteService, times(1)).save(newCliente);
    }

    @Test
    void testDeleteCliente() throws Exception{
        // Configuración de datos de prueba
        Long clienteId = 1L;

        // Llamar al método del controlador
        ResponseEntity<?> response = clienteController.delete(clienteId);

        // Validar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // Verificar que el servicio fue llamado correctamente
        verify(clienteService, times(1)).delete(clienteId);
    }
}