package com.aquasite.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    
    @Column(name = "sobre_mim")
    private String sobreMim;
    
    @Column(name = "posts")
    private Integer posts = 0;
    
    @Column(name = "seguidores")
    private Integer seguidores = 0;
    
    @Column(name = "seguindo")
    private Integer seguindo = 0;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    
    public String getSobreMim() { return sobreMim; }
    public void setSobreMim(String sobreMim) { this.sobreMim = sobreMim; }
    
    public Integer getPosts() { return posts; }
    public void setPosts(Integer posts) { this.posts = posts; }
    
    public Integer getSeguidores() { return seguidores; }
    public void setSeguidores(Integer seguidores) { this.seguidores = seguidores; }
    
    public Integer getSeguindo() { return seguindo; }
    public void setSeguindo(Integer seguindo) { this.seguindo = seguindo; }
}