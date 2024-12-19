package com.example.Backend.Controller;

import com.example.Backend.Entity.Cliente;
import com.example.Backend.IService.IClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/api/cliente")
public class ClienteController extends BaseController<Cliente, IClienteService> {
    public ClienteController(IClienteService service) {
        super(service, "Cliente");
    }

}
