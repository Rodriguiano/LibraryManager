package models;

import exceptions.LivroEmprestadoException;
import exceptions.LivroNaoEncontradoException;
import exceptions.UsuarioNaoEncontradoException;

import java.io.IOException;
import java.util.List;

public interface BibliotecaInterface {
    void cadastrarLivro(Livro livro) throws IOException;
    void removerLivro(String isbn) throws LivroNaoEncontradoException, IOException;
    Livro pesquisarLivro(String isbn);
    List<Livro> listarLivros();
    void cadastrarUsuario(Usuario usuario) throws IOException;
    void removerUsuario(String id) throws UsuarioNaoEncontradoException, IOException;
    List<Usuario> listarUsuarios();
    void emprestarLivro(String isbn, String usuarioId) throws LivroNaoEncontradoException, UsuarioNaoEncontradoException, LivroEmprestadoException;
    void devolverLivro(String isbn) throws LivroNaoEncontradoException;
    void salvarDados() throws IOException;
    void recuperarDados();
    List<Livro> buscarLivrosPorNome(String nome);
    List<Livro> buscarLivrosPorAutor(String autor);
    List<Livro> buscarLivrosPorAno(int ano);
}
