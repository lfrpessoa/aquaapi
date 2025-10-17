package com.aquasite.controller;

import com.aquasite.model.entity.Login;
import com.aquasite.service.CadastroService;
import com.aquasite.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cadastro")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class AuthController {
    
    @Autowired
    private CadastroService cadastroService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/auth/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody Login login) {
        System.out.println("=== ENDPOINT /auth/register CHAMADO ===");
        try {
            System.out.println("Recebido: nome=" + login.getNome() + ", senha=" + login.getSenha());
            
            // Verificar se já existe
            Login existingUser = cadastroService.buscarPorNome(login.getNome());
            if (existingUser != null) {
                return ResponseEntity.badRequest().body("{\"error\":\"Usuário já existe\"}");
            }
            
            System.out.println("Tentando salvar no banco...");
            Login savedLogin = cadastroService.salvar(login);
            
            // Criar perfil do usuário
            String email = login.getNome() + "@aquasite.com";
            usuarioService.criarPerfil(login.getNome(), email);
            
            System.out.println("Salvo com sucesso! ID: " + savedLogin.getId() + ", Nome: " + savedLogin.getNome());
            return ResponseEntity.ok().body("{\"message\":\"Usuário registrado com sucesso\", \"id\":\"" + savedLogin.getId() + "\"}");
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            Login user = cadastroService.buscarPorNome(login.getNome());
            if (user != null && user.getSenha().equals(login.getSenha())) {
                return ResponseEntity.ok().body("{\"message\":\"Login realizado com sucesso\", \"token\":\"fake-token\"}");
            }
            return ResponseEntity.badRequest().body("{\"error\":\"Usuário ou senha inválidos\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        System.out.println("=== ENDPOINT /test CHAMADO ===");
        return ResponseEntity.ok().body("{\"message\":\"API funcionando\"}");
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios() {
        try {
            var usuarios = cadastroService.listarTodos();
            System.out.println("Total de usuários no banco: " + usuarios.size());
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/user/check/{nome}")
    public ResponseEntity<?> checkUser(@PathVariable String nome) {
        try {
            Login user = cadastroService.buscarPorNome(nome);
            boolean exists = user != null;
            System.out.println("Verificando usuário: " + nome + ", existe: " + exists);
            return ResponseEntity.ok().body("{\"exists\":" + exists + "}");
        } catch (Exception e) {
            System.err.println("Erro ao verificar usuário: " + e.getMessage());
            return ResponseEntity.ok().body("{\"exists\":false}");
        }
    }
}