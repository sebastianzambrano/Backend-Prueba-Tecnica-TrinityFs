package com.example.Backend.Service;

import com.example.Backend.Entity.Cliente;
import com.example.Backend.IRepository.IClienteRepository;
import com.example.Backend.IService.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private IClienteRepository clienteRepository;
    @Mock
    private IProductoService productoService;
    @InjectMocks
    private ClienteService clienteService;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setCorreoElectronico("juan.perez@dominio.com");
        cliente.setFechaNacimiento(LocalDate.from(LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0)));
        cliente.setCreatedAt(LocalDateTime.now());
        cliente.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testSaveClienteValido() throws Exception {
        // Simular el comportamiento del repository
        Mockito.when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Llamar al método de servicio
        Cliente savedCliente = clienteService.save(cliente);

        // Validar resultados
        assertNotNull(savedCliente);
        assertEquals(cliente.getNombre(), savedCliente.getNombre());
        assertEquals(cliente.getCorreoElectronico(), savedCliente.getCorreoElectronico());
    }

    @Test
    void testSaveClienteInvalidoPorNombre() {
        // Modificar el cliente para que su nombre sea inválido
        cliente.setNombre("J");
        cliente.setApellido("P");

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.save(cliente);
        });

        assertEquals("El nombre debe tener al menos 2 caracteres", exception.getMessage());
    }

    @Test
    void testDeleteClienteConCuentas() throws Exception {
        // Simular que el cliente tiene cuentas asociadas
        Mockito.when(productoService.contarCuentasByClienteId(cliente.getId())).thenReturn(1L);

        // Verificar que se lanza una excepción porque el cliente tiene cuentas asociadas
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.delete(cliente.getId());
        });

        assertEquals("La persona tiene cuentas asociadas", exception.getMessage());
    }

    @Test
    void testDeleteClienteSinCuentas() throws Exception {
        // Simular que el cliente no tiene cuentas asociadas
        Mockito.when(productoService.contarCuentasByClienteId(cliente.getId())).thenReturn(0L);
        Mockito.when(clienteRepository.findById(cliente.getId())).thenReturn(Optional.of(cliente));

        // Llamar al método delete
        clienteService.delete(cliente.getId());

        // Verificar que el método delete en el repository se haya llamado
        Mockito.verify(clienteRepository, Mockito.times(1)).save(any(Cliente.class));
    }

    @Test
    void testValidarCorreoElectronicoValido() {
        boolean esValido = clienteService.esCorreoElectronicoValido("juan.perez@dominio.com");
        assertTrue(esValido);
    }

    @Test
    void testValidarCorreoElectronicoInvalido() {
        boolean esValido = clienteService.esCorreoElectronicoValido("juan.perez@dominio");
        assertFalse(esValido);
    }

    @Test
    void testCalcularEdad() {
        LocalDateTime fechaNacimiento = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
        Integer edad = clienteService.calcularEdad(fechaNacimiento);
        assertEquals(24, edad);  // Suponiendo que la prueba se hace en 2024
    }

    @Test
    void testValidarEdadClienteMenorDe18() {
        cliente.setFechaNacimiento(LocalDate.from(LocalDateTime.of(2010, Month.JANUARY, 1, 0, 0)));

        // Verificar que se lanza una excepción
        Exception exception = assertThrows(Exception.class, () -> {
            clienteService.save(cliente);
        });

        assertEquals("La edad de la persona debe ser igual o superior a 18 años", exception.getMessage());
    }
}