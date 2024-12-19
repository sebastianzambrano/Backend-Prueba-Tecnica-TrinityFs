package com.example.Backend.Controller;

import com.example.Backend.DTO.ApiResponseDto;
import com.example.Backend.Entity.Transaccion;
import com.example.Backend.IService.ITransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/api/transaccion")
public class TransaccionController extends BaseController<Transaccion, ITransaccionService> {
    public TransaccionController(ITransaccionService service) {
        super(service, "Transaccion");
    }
}
