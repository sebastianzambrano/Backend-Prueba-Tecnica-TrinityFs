package com.example.Backend.Service;

import com.example.Backend.Entity.Cliente;
import com.example.Backend.IRepository.IClienteRepository;
import com.example.Backend.IRepository.IProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService; // El servicio que estamos probando

    @Mock
    private IClienteRepository clienteRepository; // Mock del repositorio de clientes

    @Mock
    private IProductoRepository productoRepository; // Mock del repositorio de productos

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setCorreoElectronico("juan.perez@mail.com");
        cliente.setFechaNacimiento(LocalDate.of(2000, 1, 1)); // Edad mayor a 18 años
    }

    @Test
    public void testSaveClienteSuccess() throws Exception {
        // Configura el mock para que el repositorio simule guardar el cliente
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Llamar al método que queremos probar
        Cliente result = clienteService.save(cliente);

        // Verificar que el resultado no es null y que el cliente guardado es el mismo que el esperado
        assertNotNull(result);
        assertEquals(cliente.getNombre(), result.getNombre());
        assertEquals(cliente.getApellido(), result.getApellido());
        verify(clienteRepository, times(1)).save(cliente); // Verifica que se haya llamado al repositorio una vez
    }

    @Test
    public void testSaveClienteWithInvalidEmail() {
        // Configurar un correo electrónico inválido
        cliente.setCorreoElectronico("correo-invalido");

        // Verificar que al intentar guardar el cliente con un correo inválido, se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> clienteService.save(cliente));
        assertEquals("El correo electrónico no tiene un formato válido (ejemplo: correo@dominio.com)", exception.getMessage());
    }

    @Test
    public void testSaveClienteWithShortName() {
        // Configurar un nombre corto
        cliente.setNombre("J");

        // Verificar que al intentar guardar el cliente con un nombre corto, se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> clienteService.save(cliente));
        assertEquals("El nombre debe tener al menos 2 caracteres", exception.getMessage());
    }

    @Test
    public void testDeleteClienteWithNoAccounts() throws Exception {
        // Simulamos que el cliente no tiene cuentas asociadas
        when(productoRepository.contarCuentasByClienteId(cliente.getId())).thenReturn(0L);

        // Verificar que el cliente se puede eliminar correctamente
        clienteService.delete(cliente.getId());

        verify(clienteRepository, times(1)).deleteById(cliente.getId());
    }

    @Test
    public void testDeleteClienteWithAccounts() {
        // Simulamos que el cliente tiene cuentas asociadas
        when(productoRepository.contarCuentasByClienteId(cliente.getId())).thenReturn(1L);

        // Verificar que al intentar eliminar un cliente con cuentas asociadas, se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> clienteService.delete(cliente.getId()));
        assertEquals("La persona tiene cuentas asociadas", exception.getMessage());
    }
}
