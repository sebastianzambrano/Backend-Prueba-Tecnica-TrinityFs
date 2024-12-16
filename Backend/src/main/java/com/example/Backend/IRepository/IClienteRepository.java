package com.example.Backend.IRepository;

import com.example.Backend.Entity.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends IBaseRepository<Cliente, Long>{
}
