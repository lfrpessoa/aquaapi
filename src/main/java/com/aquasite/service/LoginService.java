package com.aquasite.service;

import com.aquasite.model.entity.Login;
import com.aquasite.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    
    @Autowired
    private LoginRepository loginRepository;
    
    public Login save(Login login) {
        System.out.println("SERVICE: Salvando usuário " + login.getNome());
        return loginRepository.save(login);
    }
    
    public Login findByNome(String nome) {
        return loginRepository.findByNome(nome);
    }
}