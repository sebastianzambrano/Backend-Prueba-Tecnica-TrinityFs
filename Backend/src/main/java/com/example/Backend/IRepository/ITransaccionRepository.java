package com.example.Backend.IRepository;

import com.example.Backend.Entity.Transaccion;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransaccionRepository extends IBaseRepository<Transaccion,Long> {
}
