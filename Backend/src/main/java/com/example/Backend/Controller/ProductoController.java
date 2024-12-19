package com.example.Backend.Controller;

import com.example.Backend.DTO.ApiResponseDto;
import com.example.Backend.DTO.IProductoDto;
import com.example.Backend.Entity.Producto;
import com.example.Backend.IService.IProductoService;
import com.example.Backend.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/api/producto")
public class ProductoController extends BaseController<Producto, IProductoService> {
    public ProductoController(IProductoService service) {
        super(service, "Producto");
    }

}
