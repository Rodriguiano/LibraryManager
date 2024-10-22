import exceptions.LivroEmprestadoException;
import exceptions.LivroNaoEncontradoException;
import exceptions.UsuarioNaoEncontradoException;
import models.Biblioteca;
import models.Livro;
import models.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TestBiblioteca {
    private Biblioteca biblioteca;
    private Livro livro;
    private Usuario usuario;

    @Before
    public void setUp() {
        biblioteca = new Biblioteca();
        livro = new Livro("123", "Livro de Teste", "Autor Teste", 2023);
        usuario = new Usuario("u1", "Usuário Teste");
    }

    @Test
    public void testeCadastrarLivro() throws IOException {
        biblioteca.cadastrarLivro(livro);
        assertEquals(livro, biblioteca.pesquisarLivro("123"));
    }

    @Test
    public void testeRemoverLivro() throws Exception {
        biblioteca.cadastrarLivro(livro);
        biblioteca.removerLivro("123");
        assertNull(biblioteca.pesquisarLivro("123"));
    }

    @Test
    public void testeBuscarLivrosPorNome() throws IOException {
        biblioteca.cadastrarLivro(livro);
        List<Livro> encontrados = biblioteca.buscarLivrosPorNome("Livro de Teste");
        assertEquals(1, encontrados.size());
        assertEquals(livro, encontrados.get(0));
    }

    @Test
    public void testeBuscarLivrosPorAutor() throws IOException {
        biblioteca.cadastrarLivro(livro);
        List<Livro> encontrados = biblioteca.buscarLivrosPorAutor("Autor Teste");
        assertEquals(1, encontrados.size());
        assertEquals(livro, encontrados.get(0));
    }

    @Test
    public void testeBuscarLivrosPorAno() throws IOException {
        biblioteca.cadastrarLivro(livro);
        List<Livro> encontrados = biblioteca.buscarLivrosPorAno(2023);
        assertEquals(1, encontrados.size());
        assertEquals(livro, encontrados.get(0));
    }

    @Test
    public void testeCadastrarUsuario() throws IOException {
        biblioteca.cadastrarUsuario(usuario);
        assertEquals(usuario, biblioteca.getUsuarios().get("u1"));
    }

    @Test
    public void testeRemoverUsuario() throws Exception {
        biblioteca.cadastrarUsuario(usuario);
        biblioteca.removerUsuario("u1");
        assertNull(biblioteca.getUsuarios().get("u1"));
    }

    @Test
    public void testeEmprestarLivro() throws Exception {
        biblioteca.cadastrarLivro(livro);
        biblioteca.cadastrarUsuario(usuario);
        biblioteca.emprestarLivro("123", "u1");
        assertEquals("u1", biblioteca.getEmprestimos().get("123"));
    }

    @Test
    public void testeDevolverLivro() throws Exception {
        biblioteca.cadastrarLivro(livro);
        biblioteca.cadastrarUsuario(usuario);
        biblioteca.emprestarLivro("123", "u1");
        biblioteca.devolverLivro("123");
        assertFalse(biblioteca.getEmprestimos().containsKey("123"));
    }

    @Test(expected = LivroNaoEncontradoException.class)
    public void testeRemoverLivroInexistente() throws Exception {
        biblioteca.removerLivro("456");
    }

    @Test(expected = LivroNaoEncontradoException.class)
    public void testeEmprestarLivroInexistente() throws Exception {
        biblioteca.emprestarLivro("456", "u1");
    }

    @Test(expected = UsuarioNaoEncontradoException.class)
    public void testeEmprestarLivroParaUsuarioInexistente() throws Exception {
        biblioteca.cadastrarLivro(livro);
        biblioteca.emprestarLivro("123", "u2");
    }

    @Test(expected = LivroEmprestadoException.class)
    public void testeEmprestarLivroJaEmprestado() throws Exception {
        biblioteca.cadastrarLivro(livro);
        biblioteca.cadastrarUsuario(usuario);
        biblioteca.emprestarLivro("123", "u1");
        biblioteca.emprestarLivro("123", "u1");
    }
}
