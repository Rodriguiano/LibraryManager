package controllers;

import models.Biblioteca;
import models.Livro;
import models.Usuario;
import persistence.Persistencia;


import java.io.IOException;
import java.util.List;

public class BibliotecaController {
    private final Biblioteca biblioteca;
    private final Persistencia persistencia;

    public BibliotecaController() {
        this.biblioteca = new Biblioteca();
        this.persistencia = new Persistencia();
        try {
            persistencia.recuperarLivros(biblioteca.getLivros());
            persistencia.recuperarUsuarios(biblioteca.getUsuarios());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarLivro(Livro livro) throws Exception {
        biblioteca.cadastrarLivro(livro);
        persistirDados();
    }

    public void cadastrarUsuario(Usuario usuario) throws Exception {
        biblioteca.cadastrarUsuario(usuario);
        persistirDados();
    }

    public Livro pesquisarLivro(String isbn) throws Exception {
        return biblioteca.pesquisarLivro(isbn);
    }

    public void removerLivro(String isbn) throws Exception {
        biblioteca.removerLivro(isbn);
        persistirDados();
    }

    public void removerUsuario(String id) throws Exception {
        biblioteca.removerUsuario(id);
        persistirDados();
    }

    public void emprestarLivro(String isbn, String usuarioId) throws Exception {
        biblioteca.emprestarLivro(isbn, usuarioId);
        persistirDados();
    }

    public void devolverLivro(String isbn) throws Exception {
        biblioteca.devolverLivro(isbn);
        persistirDados();
    }

    public List<Livro> buscarLivrosPorNome(String nome) {
        return biblioteca.buscarLivrosPorNome(nome);
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return biblioteca.buscarLivrosPorAutor(autor);
    }

    public List<Livro> buscarLivrosPorAno(int ano) {
        return biblioteca.buscarLivrosPorAno(ano);
    }

    public List<Livro> listarLivros() {
        return biblioteca.listarLivros();
    }

    public List<Usuario> listarUsuarios() {
        return biblioteca.listarUsuarios();
    }

    private void persistirDados() {
        try {
            persistencia.salvarLivros(biblioteca.getLivros());
            persistencia.salvarUsuarios(biblioteca.getUsuarios());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
