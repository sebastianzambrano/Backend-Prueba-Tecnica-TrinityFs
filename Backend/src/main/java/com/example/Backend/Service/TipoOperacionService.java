package com.example.Backend.Service;

import com.example.Backend.Entity.TipoOperacion;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IRepository.ITipoOperacionRepository;
import com.example.Backend.IService.ITipoOperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoOperacionService extends BaseService<TipoOperacion> implements ITipoOperacionService {
    @Autowired
    private ITipoOperacionRepository repository;
    @Override
    protected IBaseRepository<TipoOperacion, Long> getRepository() {
        return repository;
    }
}
