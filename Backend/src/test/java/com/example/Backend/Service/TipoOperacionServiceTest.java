package com.example.Backend.Service;

import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.IRepository.ITipoOperacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoOperacionServiceTest {

    @Mock
    private ITipoOperacionRepository tipoOperacionRepository;

    @InjectMocks
    private TipoOperacionService tipoOperacionService;

    private TipoOperacion tipoOperacion;

    @BeforeEach
    public void setUp() {
        tipoOperacion = new TipoOperacion();
        tipoOperacion.setId(1L);
        tipoOperacion.setDescripcion("Transferencia");
    }

    @Test
    void testFindById() throws Exception {
        // Arrange: Configuramos el comportamiento del repositorio simulado
        when(tipoOperacionRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoOperacion));

        // Act: Llamamos al servicio
        TipoOperacion result = tipoOperacionService.findById(1L);

        // Assert: Verificamos que la llamada haya retornado el objeto esperado
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Transferencia", result.getDescripcion());
    }

    @Test
    void testSave() throws Exception{
        // Arrange: Configuramos el repositorio simulado para que guarde el objeto correctamente
        when(tipoOperacionRepository.save(tipoOperacion)).thenReturn(tipoOperacion);

        // Act: Llamamos al método save del servicio
        TipoOperacion result = tipoOperacionService.save(tipoOperacion);

        // Assert: Verificamos que el resultado no sea nulo y sea igual al objeto esperado
        assertNotNull(result);
        assertEquals("Transferencia", result.getDescripcion());
    }

    @Test
    void testDelete() throws Exception {
        // Crear una entidad de prueba
        TipoOperacion tipoOperacion = new TipoOperacion();
        tipoOperacion.setId(1L);
        tipoOperacion.setDescripcion("retiro");
        tipoOperacion.setState(Boolean.TRUE);
        tipoOperacion.setDeletedAt(null);

        // Simular que findById devuelve la entidad
        when(tipoOperacionRepository.findById(1L)).thenReturn(Optional.of(tipoOperacion));

        // Configurar save para que devuelva la misma entidad
        when(tipoOperacionRepository.save(any(TipoOperacion.class))).thenReturn(tipoOperacion);

        // Act: Llamar al método delete del servicio
        tipoOperacionService.delete(1L);

        // Assert: Verificar que el método save haya sido invocado
        verify(tipoOperacionRepository, times(1)).save(any(TipoOperacion.class));  // Verificamos que save haya sido llamado
    }

    @Test
    void testDeleteNotFound() {
        // Arrange: Simulamos que el repositorio no encuentre el objeto
        when(tipoOperacionRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act & Assert: Llamamos al método delete y esperamos que lance una excepción
        Exception exception = assertThrows(Exception.class, () -> tipoOperacionService.delete(1L));

        // Act & Assert: Llamamos al método delete y esperamos que lance una excepción
        assertThrows(Exception.class, () -> tipoOperacionService.delete(1L));
    }
}