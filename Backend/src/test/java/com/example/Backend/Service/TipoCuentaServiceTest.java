package com.example.Backend.Service;

import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.IRepository.ITipoCuentaRepository;
import com.example.Backend.IService.ITipoCuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TipoCuentaServiceTest {

    @Mock
    private ITipoCuentaRepository tipoCuentaRepository;

    @InjectMocks
    private TipoCuentaService tipoCuentaService;

    private TipoCuenta tipoCuenta;

    @BeforeEach
    public void setUp() {
        tipoCuenta = new TipoCuenta();
        tipoCuenta.setId(1L);
        tipoCuenta.setDescripcion("Ahorros");
        tipoCuenta.setDeletedAt(null);  // Asegúrate de que no esté marcado como eliminado
        tipoCuenta.setState(Boolean.TRUE);  // Asegúrate de que el estado sea verdadero
    }


    @Test
    void testFindById() throws Exception {
        // Arrange: Configuramos el comportamiento del repositorio simulado
        when(tipoCuentaRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoCuenta));

        // Act: Llamamos al servicio
        TipoCuenta result = tipoCuentaService.findById(1L);

        // Assert: Verificamos que la llamada haya retornado el objeto esperado
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ahorros", result.getDescripcion());
    }

    @Test
    void testSave() throws Exception{
        // Arrange: Configuramos el repositorio simulado para que guarde el objeto correctamente
        when(tipoCuentaRepository.save(tipoCuenta)).thenReturn(tipoCuenta);

        // Act: Llamamos al método save del servicio
        TipoCuenta result = tipoCuentaService.save(tipoCuenta);

        // Assert: Verificamos que el resultado no sea nulo y sea igual al objeto esperado
        assertNotNull(result);
        assertEquals("Ahorros", result.getDescripcion());
    }

    @Test
    void testDelete() throws Exception {
        // Crear una entidad de prueba
        TipoCuenta tipoCuenta = new TipoCuenta();
        tipoCuenta.setId(1L);
        tipoCuenta.setDescripcion("Ahorros");
        tipoCuenta.setState(Boolean.TRUE);
        tipoCuenta.setDeletedAt(null);

        // Simular que findById devuelve la entidad
        when(tipoCuentaRepository.findById(1L)).thenReturn(Optional.of(tipoCuenta));

        // Configurar save para que devuelva la misma entidad
        when(tipoCuentaRepository.save(any(TipoCuenta.class))).thenReturn(tipoCuenta);

        // Act: Llamar al método delete del servicio
        tipoCuentaService.delete(1L);

        // Assert: Verificar que el método save haya sido invocado
        verify(tipoCuentaRepository, times(1)).save(any(TipoCuenta.class));  // Verificamos que save haya sido llamado
    }

    @Test
    void testDeleteNotFound() {
        // Simulamos que el repositorio no encuentre el objeto
        when(tipoCuentaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Llamamos al método delete y esperamos que lance una excepción
        Exception exception = assertThrows(Exception.class, () -> tipoCuentaService.delete(1L));

        // Verificamos que el mensaje de la excepción sea el esperado
        assertNotNull(exception);
        assertEquals("Registro no encontrado", exception.getMessage());
    }
}