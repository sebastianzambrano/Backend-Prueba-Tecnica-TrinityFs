package com.example.Backend.Service;

import com.example.Backend.Entity.Cliente;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IRepository.IClienteRepository;
import com.example.Backend.IRepository.IProductoRepository;
import com.example.Backend.IService.IClienteService;
import com.example.Backend.IService.IProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.regex.Pattern;

@Service
public class ClienteService extends BaseService<Cliente> implements IClienteService {
    @Autowired
    private IClienteRepository repository;
    @Autowired
    private IProductoService productoService;
    @Override
    protected IBaseRepository<Cliente, Long> getRepository() { return repository; }
    @Transactional
    @Override
    public void delete(Long id)throws Exception{
        System.out.println("se esta eliminando cliente");
        System.out.println("valor igual: " + productoService.contarCuentasByClienteId(id));
        if(productoService.contarCuentasByClienteId(id)>0){
            throw new Exception("La persona tiene cuentas asociadas");}

        super.delete(id);
    }
    @Transactional
    @Override
    public Cliente save(Cliente cliente) throws Exception {
        validarCliente(cliente);
        cliente.setCreatedAt(LocalDateTime.now());
        cliente.setUpdatedAt(LocalDateTime.now());
        return super.save(cliente);
    }
    private void validarCliente(Cliente cliente) throws Exception {

        if (cliente.getNombre() == null || cliente.getNombre().length() < 2) {
            throw new Exception("El nombre debe tener al menos 2 caracteres");
        }

        if (cliente.getApellido() == null || cliente.getApellido().length() < 2) {
            throw new Exception("El apellido debe tener al menos 2 caracteres");
        }

        if (cliente.getCorreoElectronico() == null || !esCorreoElectronicoValido(cliente.getCorreoElectronico())) {
            throw new Exception("El correo electrónico no tiene un formato válido (ejemplo: correo@dominio.com)");
        }

        if (cliente.getFechaNacimiento() == null || calcularEdad(cliente.getFechaNacimiento().atStartOfDay())<18){
            throw  new Exception("La edad de la persona debe ser igual o superior a 18 años");
        }
    }
    private boolean esCorreoElectronicoValido(String correoElectronico) {
        String regexCorreo = "^[\\w-    \\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regexCorreo);
        return pattern.matcher(correoElectronico).matches();
    }
    private Integer calcularEdad(LocalDateTime fechaNacimiento){
        LocalDate fechaNacimientoLocalDate = fechaNacimiento.toLocalDate();
        LocalDate fechaActual = LocalDate.now();
        return Period.between(fechaNacimientoLocalDate, fechaActual).getYears();
    }
}
