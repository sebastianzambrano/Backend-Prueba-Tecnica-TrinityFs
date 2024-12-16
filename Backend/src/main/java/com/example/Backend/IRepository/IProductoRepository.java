package com.example.Backend.IRepository;

import com.example.Backend.Entity.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends IBaseRepository<Producto, Long>{
}
