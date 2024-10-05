import controllers.BibliotecaController;
import gui.BibliotecaGUI;

public class Main {
    public static void main(String[] args) {
        BibliotecaController controller = new BibliotecaController();
        new BibliotecaGUI(controller);
    }
}
