package gui;

import controllers.BibliotecaController;
import models.Livro;
import models.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BibliotecaGUI {
    private final BibliotecaController controller;

    public BibliotecaGUI(BibliotecaController controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        JFrame frame = new JFrame("Sistema de Biblioteca");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel livroPanel = createLivroPanel();
        JPanel usuarioPanel = createUsuarioPanel();
        JPanel emprestimoPanel = createEmprestimoPanel();

        tabbedPane.add("Livros", livroPanel);
        tabbedPane.add("Usuários", usuarioPanel);
        tabbedPane.add("Empréstimos", emprestimoPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createLivroPanel() {
        JPanel livroPanel = new JPanel();
        livroPanel.setLayout(new BoxLayout(livroPanel, BoxLayout.Y_AXIS));
        livroPanel.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Livros"));

        JTextField isbnField = new JTextField(15);
        JTextField tituloField = new JTextField(15);
        JTextField autorField = new JTextField(15);
        JTextField anoField = new JTextField(4);

        livroPanel.add(new JLabel("ISBN:"));
        livroPanel.add(isbnField);
        livroPanel.add(new JLabel("Título:"));
        livroPanel.add(tituloField);
        livroPanel.add(new JLabel("Autor:"));
        livroPanel.add(autorField);
        livroPanel.add(new JLabel("Ano:"));
        livroPanel.add(anoField);

        JButton cadastrarLivroButton = new JButton("Cadastrar");
        cadastrarLivroButton.addActionListener(e -> {
            try {
                String isbn = isbnField.getText();
                String titulo = tituloField.getText();
                String autor = autorField.getText();
                int ano = Integer.parseInt(anoField.getText());

                if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                    throw new IllegalArgumentException("Todos os campos são obrigatórios.");
                }

                controller.cadastrarLivro(new Livro(isbn, titulo, autor, ano));
                JOptionPane.showMessageDialog(livroPanel, "Livro cadastrado com sucesso!");
                clearFields(isbnField, tituloField, autorField, anoField);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(livroPanel, "Ano deve ser um número inteiro.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(livroPanel, ex.getMessage());
            }
        });

        JButton listarLivrosButton = new JButton("Listar");
        listarLivrosButton.addActionListener(e -> {
            List<Livro> livros = controller.listarLivros();
            StringBuilder sb = new StringBuilder("Livros cadastrados:\n");
            for (Livro livro : livros) {
                sb.append(livro.getTitulo()).append(" (").append(livro.getIsbn()).append(")\n");
            }
            JOptionPane.showMessageDialog(livroPanel, sb.toString());
        });

        JButton removerLivroButton = new JButton("Remover");
        removerLivroButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(livroPanel, "Informe o ISBN do Livro:");
            if (isbn != null) {
                int confirm = JOptionPane.showConfirmDialog(livroPanel, "Deseja remover o livro com ISBN: " + isbn + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.removerLivro(isbn);
                        JOptionPane.showMessageDialog(livroPanel, "Livro removido com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(livroPanel, ex.getMessage());
                    }
                }
            }
        });

        JButton pesquisarLivroButton = new JButton("Pesquisar Livro");
        pesquisarLivroButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(livroPanel, "Informe o ISBN do Livro:");
            try {
                Livro livro = controller.pesquisarLivro(isbn);
                JOptionPane.showMessageDialog(livroPanel, "Livro encontrado: " + livro);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(livroPanel, ex.getMessage());
            }
        });

        JButton buscarLivrosPorNomeButton = new JButton("Buscar Livros por Nome");
        buscarLivrosPorNomeButton.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(livroPanel, "Informe o nome do livro:");
            List<Livro> livros = controller.buscarLivrosPorNome(nome);
            StringBuilder sb = new StringBuilder("Livros encontrados:\n");
            for (Livro livro : livros) {
                sb.append(livro).append("\n");
            }
            JOptionPane.showMessageDialog(livroPanel, sb.toString());
        });

        JButton buscarLivrosPorAutorButton = new JButton("Buscar Livros por Autor");
        buscarLivrosPorAutorButton.addActionListener(e -> {
            String autor = JOptionPane.showInputDialog(livroPanel, "Informe o autor:");
            List<Livro> livros = controller.buscarLivrosPorAutor(autor);
            StringBuilder sb = new StringBuilder("Livros encontrados:\n");
            for (Livro livro : livros) {
                sb.append(livro).append("\n");
            }
            JOptionPane.showMessageDialog(livroPanel, sb.toString());
        });

        JButton buscarLivrosPorAnoButton = new JButton("Buscar Livros por Ano");
        buscarLivrosPorAnoButton.addActionListener(e -> {
            String anoStr = JOptionPane.showInputDialog(livroPanel, "Informe o ano:");
            try {
                int ano = Integer.parseInt(anoStr);
                List<Livro> livros = controller.buscarLivrosPorAno(ano);
                StringBuilder sb = new StringBuilder("Livros encontrados:\n");
                for (Livro livro : livros) {
                    sb.append(livro).append("\n");
                }
                JOptionPane.showMessageDialog(livroPanel, sb.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(livroPanel, "Ano deve ser um número inteiro.");
            }
        });

        livroPanel.add(cadastrarLivroButton);
        livroPanel.add(listarLivrosButton);
        livroPanel.add(removerLivroButton);
        livroPanel.add(pesquisarLivroButton);
        livroPanel.add(buscarLivrosPorNomeButton);
        livroPanel.add(buscarLivrosPorAutorButton);
        livroPanel.add(buscarLivrosPorAnoButton);

        return livroPanel;
    }

    private JPanel createUsuarioPanel() {
        JPanel usuarioPanel = new JPanel();
        usuarioPanel.setLayout(new BoxLayout(usuarioPanel, BoxLayout.Y_AXIS));
        usuarioPanel.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Usuários"));

        JTextField idUsuarioField = new JTextField(15);
        JTextField nomeUsuarioField = new JTextField(15);

        usuarioPanel.add(new JLabel("ID Usuário:"));
        usuarioPanel.add(idUsuarioField);
        usuarioPanel.add(new JLabel("Nome:"));
        usuarioPanel.add(nomeUsuarioField);

        JButton cadastrarUsuarioButton = new JButton("Cadastrar");
        cadastrarUsuarioButton.addActionListener(e -> {
            try {
                String id = idUsuarioField.getText();
                String nome = nomeUsuarioField.getText();

                if (id.isEmpty() || nome.isEmpty()) {
                    throw new IllegalArgumentException("Todos os campos são obrigatórios.");
                }

                controller.cadastrarUsuario(new Usuario(id, nome));
                JOptionPane.showMessageDialog(usuarioPanel, "Usuário cadastrado com sucesso!");
                clearFields(idUsuarioField, nomeUsuarioField);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(usuarioPanel, ex.getMessage());
            }
        });

        JButton listarUsuariosButton = new JButton("Listar");
        listarUsuariosButton.addActionListener(e -> {
            List<Usuario> usuarios = controller.listarUsuarios();
            StringBuilder sb = new StringBuilder("Usuários cadastrados:\n");
            for (Usuario usuario : usuarios) {
                sb.append(usuario.getNome()).append(" (ID: ").append(usuario.getId()).append(")\n");
            }
            JOptionPane.showMessageDialog(usuarioPanel, sb.toString());
        });

        JButton removerUsuarioButton = new JButton("Remover");
        removerUsuarioButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(usuarioPanel, "Informe o ID do Usuário:");
            if (id != null) {
                int confirm = JOptionPane.showConfirmDialog(usuarioPanel, "Deseja remover o usuário com ID: " + id + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.removerUsuario(id);
                        JOptionPane.showMessageDialog(usuarioPanel, "Usuário removido com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(usuarioPanel, ex.getMessage());
                    }
                }
            }
        });

        usuarioPanel.add(cadastrarUsuarioButton);
        usuarioPanel.add(listarUsuariosButton);
        usuarioPanel.add(removerUsuarioButton);

        return usuarioPanel;
    }

    private JPanel createEmprestimoPanel() {
        JPanel emprestimoPanel = new JPanel();
        emprestimoPanel.setLayout(new BoxLayout(emprestimoPanel, BoxLayout.Y_AXIS));
        emprestimoPanel.setBorder(BorderFactory.createTitledBorder("Empréstimo e Devolução"));

        JButton emprestarLivroButton = new JButton("Emprestar Livro");
        emprestarLivroButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(emprestimoPanel, "ISBN do Livro:");
            String usuarioId = JOptionPane.showInputDialog(emprestimoPanel, "ID do Usuário:");
            try {
                controller.emprestarLivro(isbn, usuarioId);
                JOptionPane.showMessageDialog(emprestimoPanel, "Livro emprestado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(emprestimoPanel, ex.getMessage());
            }
        });

        JButton devolverLivroButton = new JButton("Devolver Livro");
        devolverLivroButton.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog(emprestimoPanel, "ISBN do Livro:");
            try {
                controller.devolverLivro(isbn);
                JOptionPane.showMessageDialog(emprestimoPanel, "Livro devolvido com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(emprestimoPanel, ex.getMessage());
            }
        });

        emprestimoPanel.add(emprestarLivroButton);
        emprestimoPanel.add(devolverLivroButton);

        return emprestimoPanel;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}
