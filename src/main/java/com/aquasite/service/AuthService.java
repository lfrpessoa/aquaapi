package com.aquasite.service;

import com.aquasite.model.entity.Login;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean autenticar(String nome, String senha) {
        // TODO: implementar autenticação via repository
        return false;
    }

    public Login registrar(Login login) {
        // TODO: implementar registro via repository
        return null;
    }

    public Login buscarPorNome(String nome) {
        // TODO: implementar busca por nome via repository
        return null;
    }
}