package com.aquasite.service;

import com.aquasite.model.entity.Usuario;
import com.aquasite.model.entity.Post;
import com.aquasite.model.entity.Seguidor;
import com.aquasite.repository.UsuarioRepository;
import com.aquasite.repository.PostRepository;
import com.aquasite.repository.SeguidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private SeguidorRepository seguidorRepository;
    
    public Usuario criarPerfil(String nome, String email) {
        // Verificar se já existe
        Usuario existente = usuarioRepository.findByNome(nome);
        if (existente != null) {
            return existente;
        }
        
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setDataCadastro(LocalDate.now());
        usuario.setSobreMim("");
        return usuarioRepository.save(usuario);
    }
    
    public Usuario buscarPorNome(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome);
        if (usuario != null) {
            atualizarContadores(usuario);
        }
        return usuario;
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario atualizarPerfil(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    
    // Métodos para Posts
    public Post criarPost(Usuario usuario, String conteudo) {
        System.out.println("SERVICE: Criando post para usuário " + usuario.getNome());
        
        Post post = new Post();
        post.setUsuario(usuario);
        post.setConteudo(conteudo);
        post.setDataCriacao(LocalDateTime.now());
        
        System.out.println("SERVICE: Salvando post no banco...");
        Post postSalvo = postRepository.save(post);
        System.out.println("SERVICE: Post salvo com ID: " + postSalvo.getId());
        
        // Atualizar contador de posts do usuário
        int totalPosts = postRepository.countByUsuario(usuario);
        System.out.println("SERVICE: Total de posts do usuário: " + totalPosts);
        usuario.setPosts(totalPosts);
        usuarioRepository.save(usuario);
        
        return postSalvo;
    }
    
    public List<Post> buscarPostsDoUsuario(Usuario usuario) {
        return postRepository.findByUsuarioOrderByDataCriacaoDesc(usuario);
    }
    
    public void deletarPost(Long postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            Usuario usuario = post.getUsuario();
            
            postRepository.deleteById(postId);
            
            // Atualizar contador de posts
            atualizarContadores(usuario);
        }
    }
    
    // Métodos para Seguidores
    public void seguirUsuario(Usuario seguidor, Usuario seguido) {
        if (!seguidorRepository.existsBySeguidorAndSeguido(seguidor, seguido)) {
            Seguidor novoSeguimento = new Seguidor();
            novoSeguimento.setSeguidor(seguidor);
            novoSeguimento.setSeguido(seguido);
            novoSeguimento.setDataSeguimento(LocalDateTime.now());
            seguidorRepository.save(novoSeguimento);
            
            // Atualizar contadores
            atualizarContadores(seguidor);
            atualizarContadores(seguido);
        }
    }
    
    public void pararDeSeguir(Usuario seguidor, Usuario seguido) {
        seguidorRepository.findAll().stream()
            .filter(s -> s.getSeguidor().equals(seguidor) && s.getSeguido().equals(seguido))
            .findFirst()
            .ifPresent(s -> {
                seguidorRepository.delete(s);
                atualizarContadores(seguidor);
                atualizarContadores(seguido);
            });
    }
    
    private void atualizarContadores(Usuario usuario) {
        int seguidores = seguidorRepository.countBySeguido(usuario);
        int seguindo = seguidorRepository.countBySeguidor(usuario);
        int posts = postRepository.countByUsuario(usuario);
        
        usuario.setSeguidores(seguidores);
        usuario.setSeguindo(seguindo);
        usuario.setPosts(posts);
        usuarioRepository.save(usuario);
    }
}