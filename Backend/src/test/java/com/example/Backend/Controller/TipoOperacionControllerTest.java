package com.example.Backend.Controller;

import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.IService.ITipoOperacionService;
import com.example.Backend.DTO.ApiResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TipoOperacionControllerTest {

  @Mock
  private ITipoOperacionService tipoOperacionService;

  @InjectMocks
  private TipoOperacionController tipoOperacionController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);  // Inicializa los mocks antes de cada prueba
  }

  @Test
  void testGetTipoOperacionById() throws Exception {
    // Configuración de datos de prueba
    Long tipoOperacionId = 1L;
    TipoOperacion mockTipoOperacion = new TipoOperacion();
    mockTipoOperacion.setId(tipoOperacionId);
    mockTipoOperacion.setDescripcion("Transferencia");

    // Simular comportamiento del servicio
    when(tipoOperacionService.findById(tipoOperacionId)).thenReturn(mockTipoOperacion);

    // Llamar al método del controlador
    ResponseEntity<?> response = tipoOperacionController.show(tipoOperacionId);

    // Validar resultados
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());

    // Crear la respuesta esperada con ApiResponseDto
    ApiResponseDto<TipoOperacion> expectedResponse = new ApiResponseDto<>("Registro encontrado", mockTipoOperacion, true);

    // Convertir la respuesta y validar los datos
    ApiResponseDto<TipoOperacion> actualResponse = (ApiResponseDto<TipoOperacion>) response.getBody();
    assertNotNull(actualResponse);
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    assertEquals(expectedResponse.getData(), actualResponse.getData());

    // Verificar que el servicio fue llamado correctamente
    verify(tipoOperacionService, times(1)).findById(tipoOperacionId);
  }

  @Test
  void testCreateTipoOperacion() throws Exception {
    // Configuración de datos de prueba
    Long tipoOperacionId = 1L;
    TipoOperacion newTipoOperacion = new TipoOperacion();
    newTipoOperacion.setDescripcion("Transferencia");

    TipoOperacion savedTipoOperacion = new TipoOperacion();
    savedTipoOperacion.setId(tipoOperacionId);
    savedTipoOperacion.setDescripcion("Transferencia");

    // Simular comportamiento del servicio
    when(tipoOperacionService.save(newTipoOperacion)).thenReturn(savedTipoOperacion);

    // Llamar al método del controlador
    ResponseEntity<?> response = tipoOperacionController.save(newTipoOperacion);

    // Validar resultados
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue());

    // Crear la respuesta esperada con ApiResponseDto
    ApiResponseDto<TipoOperacion> expectedResponse = new ApiResponseDto<>("Datos guardados", savedTipoOperacion, true);

    // Extraer el cuerpo como ApiResponseDto
    ApiResponseDto<TipoOperacion> actualResponse = (ApiResponseDto<TipoOperacion>) response.getBody();
    assertNotNull(actualResponse);

    // Validar contenido del ApiResponseDto
    assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
    assertEquals(expectedResponse.getData(), actualResponse.getData());

    // Verificar que el servicio fue llamado correctamente
    verify(tipoOperacionService, times(1)).save(newTipoOperacion);
  }

  @Test
  void testDeleteTipoOperacion() throws Exception {
    // Configuración de datos de prueba
    Long tipoOperacionId = 1L;

    // Llamar al método del controlador
    ResponseEntity<?> response = tipoOperacionController.delete(tipoOperacionId);

    // Validar resultados
    assertNotNull(response);
    assertEquals(200, response.getStatusCodeValue()); // o 204 si se espera No Content

    // Verificar que el servicio fue llamado correctamente
    verify(tipoOperacionService, times(1)).delete(tipoOperacionId);
  }
}