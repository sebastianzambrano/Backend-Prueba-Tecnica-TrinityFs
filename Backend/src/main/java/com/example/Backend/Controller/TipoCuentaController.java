package com.example.Backend.Controller;

import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.IService.ITipoCuentaService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/api/tipo-cuenta")
public class TipoCuentaController extends BaseController<TipoCuenta, ITipoCuentaService> {
    public TipoCuentaController(ITipoCuentaService service) {
        super(service, "TipoCuenta");
    }
}
