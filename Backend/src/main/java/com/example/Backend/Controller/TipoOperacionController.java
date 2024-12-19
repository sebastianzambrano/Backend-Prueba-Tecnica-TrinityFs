package com.example.Backend.Controller;

import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.IService.ITipoOperacionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/api/tipo-operacion")
public class TipoOperacionController extends BaseController<TipoOperacion, ITipoOperacionService> {
    public TipoOperacionController(ITipoOperacionService service) {
        super(service, "TipoOperacion");
    }
}
