package com.aquasite.service;

import com.aquasite.model.entity.Login;
import com.aquasite.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroService {

    @Autowired
    private LoginRepository loginRepository;

    public List<Login> listarTodos() {
        return loginRepository.findAll();
    }

    public Optional<Login> buscarPorId(Long id) {
        return loginRepository.findById(id);
    }

    public Login salvar(Login login) {
        return loginRepository.save(login);
    }

    public Login buscarPorNome(String nome) {
        return loginRepository.findByNome(nome);
    }

    public void deletar(Long id) {
        loginRepository.deleteById(id);
    }
}