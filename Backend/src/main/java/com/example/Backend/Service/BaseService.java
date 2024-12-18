package com.example.Backend.Service;

import com.example.Backend.Entity.Auditoria;
import com.example.Backend.IRepository.IBaseRepository;
import com.example.Backend.IService.IBaseService;
import org.springframework.beans.BeanUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends Auditoria> implements IBaseService<T> {
    protected abstract IBaseRepository<T, Long> getRepository();

    @Override
    public List<T> all() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findByStateTrue() {
        return getRepository().findAll();
    }

    @Override
    public T findById(Long id) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()) {
            throw new Exception("Registro no encontrado");
        }

        return op.get();
    }


    @Override
    public T save(T entity) throws Exception{
        try {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setState(Boolean.TRUE);
            return getRepository().save(entity);
        } catch (Exception e) {
            // Captura la excepción
            throw new Exception("Error al guardar la entidad: " + e.getMessage());
        }
    }


    @Override
    public void update(Long id, T entity) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()) {
            throw new Exception("Registro no encontrado");
        }else if(op.get().getDeletedAt() != null) {
            throw new Exception("Registro inhabilitado");
        }

        T entityUpdate = op.get();

        String[] ignoreProperties = {"id","createdAt","deleteAt"};
        BeanUtils.copyProperties(entity, entityUpdate,ignoreProperties);
        entityUpdate.setUpdatedAt(LocalDateTime.now());
        getRepository().save(entityUpdate);
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()||op.get().getDeletedAt() != null) {
            throw new Exception("Registro no encontrado");
        }

        T entityUpdate = op.get();
        entityUpdate.setDeletedAt(LocalDateTime.now());
        entityUpdate.setState(Boolean.FALSE);

        getRepository().save(entityUpdate);
    }
}
