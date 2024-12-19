package com.example.Backend.IRepository;

import com.example.Backend.Entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IBaseRepository <T extends Auditoria, ID> extends JpaRepository<T,ID> {
}
