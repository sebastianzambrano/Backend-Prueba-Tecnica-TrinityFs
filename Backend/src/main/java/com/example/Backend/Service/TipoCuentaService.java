package com.example.Backend.Service;

import com.example.Backend.Entity.TipoCuenta;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IRepository.ITipoCuentaRepository;
import com.example.Backend.IService.ITipoCuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoCuentaService extends BaseService<TipoCuenta> implements ITipoCuentaService {
    @Autowired
    private ITipoCuentaRepository repository;
    @Override
    protected IBaseRepository<TipoCuenta, Long> getRepository() {
        return repository;
    }
}
