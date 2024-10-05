package models;

import exceptions.LivroEmprestadoException;
import exceptions.LivroNaoEncontradoException;
import exceptions.UsuarioNaoEncontradoException;
import persistence.Persistencia;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Biblioteca implements BibliotecaInterface, Serializable {
    private Map<String, Livro> livros = new HashMap<>();
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Map<String, String> emprestimos = new HashMap<>(); // Mapeia ISBN para ID do usuário
    private Persistencia persistencia = new Persistencia();

    public Biblioteca() {
        recuperarDados(); // Recupera dados ao iniciar a biblioteca
    }

    @Override
    public void cadastrarLivro(Livro livro) throws IOException {
        livros.put(livro.getIsbn(), livro);
        salvarDados(); // Salvar após cadastrar
    }

    @Override
    public void removerLivro(String isbn) throws LivroNaoEncontradoException, IOException {
        if (livros.containsKey(isbn)) {
            livros.remove(isbn);
            salvarDados(); // Salvar após remover
        } else {
            throw new LivroNaoEncontradoException("Livro não encontrado!");
        }
    }

    @Override
    public Livro pesquisarLivro(String isbn) {
        return livros.get(isbn);
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros.values());
    }

    public List<Livro> buscarLivrosPorNome(String nome) {
        return livros.values().stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livros.values().stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAno(int ano) {
        return livros.values().stream()
                .filter(livro -> livro.getAno() == ano)
                .collect(Collectors.toList());
    }

    public Map<String, Livro> getLivros() {
        return livros;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public void cadastrarUsuario(Usuario usuario) throws IOException {
        usuarios.put(usuario.getId(), usuario);
        salvarDados(); // Salvar após cadastrar
    }

    @Override
    public void removerUsuario(String id) throws UsuarioNaoEncontradoException, IOException {
        if (usuarios.containsKey(id)) {
            usuarios.remove(id);
            salvarDados(); // Salvar após remover
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());
    }

    public void emprestarLivro(String isbn, String usuarioId) throws LivroNaoEncontradoException, UsuarioNaoEncontradoException, LivroEmprestadoException {
        if (!livros.containsKey(isbn)) {
            throw new LivroNaoEncontradoException("Livro não encontrado!");
        }
        if (!usuarios.containsKey(usuarioId)) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
        if (emprestimos.containsKey(isbn)) {
            throw new LivroEmprestadoException("Livro já emprestado!");
        }
        emprestimos.put(isbn, usuarioId);
    }

    public void devolverLivro(String isbn) throws LivroNaoEncontradoException {
        if (!emprestimos.containsKey(isbn)) {
            throw new LivroNaoEncontradoException("Este livro não está emprestado!");
        }
        emprestimos.remove(isbn);
    }

    public Map<String, String> getEmprestimos() {
        return emprestimos;
    }

    @Override
    public void salvarDados() throws IOException {
        persistencia.salvarLivros(livros);
        persistencia.salvarUsuarios(usuarios);
    }

    public void recuperarDados() {
        try {
            persistencia.recuperarLivros(livros);
            persistencia.recuperarUsuarios(usuarios);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


