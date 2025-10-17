package com.aquasite.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguidor")
public class Seguidor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "seguidor_id")
    private Usuario seguidor;
    
    @ManyToOne
    @JoinColumn(name = "seguido_id")
    private Usuario seguido;
    
    @Column(name = "data_seguimento")
    private LocalDateTime dataSeguimento;
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Usuario getSeguidor() { return seguidor; }
    public void setSeguidor(Usuario seguidor) { this.seguidor = seguidor; }
    
    public Usuario getSeguido() { return seguido; }
    public void setSeguido(Usuario seguido) { this.seguido = seguido; }
    
    public LocalDateTime getDataSeguimento() { return dataSeguimento; }
    public void setDataSeguimento(LocalDateTime dataSeguimento) { this.dataSeguimento = dataSeguimento; }
}