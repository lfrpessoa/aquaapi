package com.aquasite.repository;

import com.aquasite.model.entity.Post;
import com.aquasite.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);
    int countByUsuario(Usuario usuario);
}