package com.aquasite.controller;

import com.aquasite.model.entity.Usuario;
import com.aquasite.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/perfil/{nome}")
    public ResponseEntity<?> buscarPerfil(@PathVariable String nome) {
        try {
            Usuario usuario = usuarioService.buscarPorNome(nome);
            if (usuario == null) {
                // Criar perfil se não existir
                String email = nome + "@aquasite.com";
                usuario = usuarioService.criarPerfil(nome, email);
                System.out.println("Perfil criado automaticamente para: " + nome);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/criar")
    public ResponseEntity<?> criarPerfil(@RequestBody Usuario usuario) {
        try {
            Usuario novoPerfil = usuarioService.criarPerfil(usuario.getNome(), usuario.getEmail());
            return ResponseEntity.ok(novoPerfil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarPerfil(@PathVariable Long id, @RequestBody Usuario dadosAtualizacao) {
        try {
            System.out.println("=== ATUALIZANDO PERFIL ID: " + id + " ===");
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
            if (!usuarioOpt.isPresent()) {
                System.out.println("Usuário com ID " + id + " não encontrado");
                return ResponseEntity.notFound().build();
            }
            
            Usuario usuario = usuarioOpt.get();
            System.out.println("Usuário encontrado: ID=" + usuario.getId() + ", Nome=" + usuario.getNome());
            
            // Atualizar apenas os campos enviados
            if (dadosAtualizacao.getNome() != null) usuario.setNome(dadosAtualizacao.getNome());
            if (dadosAtualizacao.getEmail() != null) usuario.setEmail(dadosAtualizacao.getEmail());
            if (dadosAtualizacao.getSobreMim() != null) {
                System.out.println("Atualizando sobre mim: " + dadosAtualizacao.getSobreMim());
                usuario.setSobreMim(dadosAtualizacao.getSobreMim());
            }
            if (dadosAtualizacao.getPosts() != null) usuario.setPosts(dadosAtualizacao.getPosts());
            if (dadosAtualizacao.getSeguidores() != null) usuario.setSeguidores(dadosAtualizacao.getSeguidores());
            if (dadosAtualizacao.getSeguindo() != null) usuario.setSeguindo(dadosAtualizacao.getSeguindo());
            
            Usuario perfilAtualizado = usuarioService.atualizarPerfil(usuario);
            System.out.println("Perfil atualizado com sucesso: ID=" + perfilAtualizado.getId());
            return ResponseEntity.ok(perfilAtualizado);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar perfil: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PutMapping("/atualizar/nome/{nome}")
    public ResponseEntity<?> atualizarPerfilPorNome(@PathVariable String nome, @RequestBody Usuario dadosAtualizacao) {
        try {
            Usuario usuario = usuarioService.buscarPorNome(nome);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Atualizar campos
            if (dadosAtualizacao.getEmail() != null) usuario.setEmail(dadosAtualizacao.getEmail());
            if (dadosAtualizacao.getSobreMim() != null) usuario.setSobreMim(dadosAtualizacao.getSobreMim());
            if (dadosAtualizacao.getPosts() != null) usuario.setPosts(dadosAtualizacao.getPosts());
            if (dadosAtualizacao.getSeguidores() != null) usuario.setSeguidores(dadosAtualizacao.getSeguidores());
            if (dadosAtualizacao.getSeguindo() != null) usuario.setSeguindo(dadosAtualizacao.getSeguindo());
            
            Usuario perfilAtualizado = usuarioService.atualizarPerfil(usuario);
            return ResponseEntity.ok(perfilAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/todos")
    public ResponseEntity<?> listarTodos() {
        try {
            return ResponseEntity.ok(usuarioService.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/post")
    public ResponseEntity<?> criarPost(@RequestParam String nome, @RequestParam String conteudo) {
        System.out.println("=== CRIANDO POST ===");
        System.out.println("Nome: " + nome);
        System.out.println("Conteúdo: " + conteudo);
        
        try {
            Usuario usuario = usuarioService.buscarPorNome(nome);
            if (usuario == null) {
                System.out.println("Usuário não encontrado: " + nome);
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("Usuário encontrado: ID=" + usuario.getId() + ", Nome=" + usuario.getNome());
            var post = usuarioService.criarPost(usuario, conteudo);
            System.out.println("Post criado com sucesso: ID=" + post.getId());
            
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            System.err.println("Erro ao criar post: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @PostMapping("/seguir")
    public ResponseEntity<?> seguirUsuario(@RequestParam String seguidor, @RequestParam String seguido) {
        try {
            Usuario userSeguidor = usuarioService.buscarPorNome(seguidor);
            Usuario userSeguido = usuarioService.buscarPorNome(seguido);
            
            if (userSeguidor == null || userSeguido == null) {
                return ResponseEntity.notFound().build();
            }
            
            usuarioService.seguirUsuario(userSeguidor, userSeguido);
            return ResponseEntity.ok("{\"message\":\"Seguindo com sucesso\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @GetMapping("/posts/{nome}")
    public ResponseEntity<?> buscarPostsDoUsuario(@PathVariable String nome) {
        try {
            Usuario usuario = usuarioService.buscarPorNome(nome);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            
            var posts = usuarioService.buscarPostsDoUsuario(usuario);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletarPost(@PathVariable Long id) {
        try {
            usuarioService.deletarPost(id);
            return ResponseEntity.ok("{\"message\":\"Post deletado com sucesso\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}