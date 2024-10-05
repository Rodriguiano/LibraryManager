package persistence;

import models.Livro;
import models.Usuario;

import java.io.*;
import java.util.Map;

public class Persistencia {
    private final String livrosFilePath = "livros.dat";
    private final String usuariosFilePath = "usuarios.dat";

    public void salvarLivros(Map<String, Livro> livros) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(livrosFilePath))) {
            oos.writeObject(livros);
        }
    }

    public void salvarUsuarios(Map<String, Usuario> usuarios) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usuariosFilePath))) {
            oos.writeObject(usuarios);
        }
    }

    public void recuperarLivros(Map<String, Livro> livros) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(livrosFilePath))) {
            Map<String, Livro> livrosRecuperados = (Map<String, Livro>) ois.readObject();
            livros.putAll(livrosRecuperados);
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, ignore
        }
    }

    public void recuperarUsuarios(Map<String, Usuario> usuarios) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usuariosFilePath))) {
            Map<String, Usuario> usuariosRecuperados = (Map<String, Usuario>) ois.readObject();
            usuarios.putAll(usuariosRecuperados);
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, ignore
        }
    }
}
