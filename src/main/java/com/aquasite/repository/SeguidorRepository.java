package com.aquasite.repository;

import com.aquasite.model.entity.Seguidor;
import com.aquasite.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguidorRepository extends JpaRepository<Seguidor, Long> {
    int countBySeguido(Usuario seguido);
    int countBySeguidor(Usuario seguidor);
    boolean existsBySeguidorAndSeguido(Usuario seguidor, Usuario seguido);
}