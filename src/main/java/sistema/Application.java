package sistema;

import sistema.logic.entities.Usuario;
import sistema.logic.entities.Departamento;
import sistema.logic.Service;
import sistema.presentation.MenuAdmin;
import sistema.presentation.MenuMedico;
import sistema.presentation.MenuFarmaceutico;
import sistema.presentation.logIn.LogInController;
import sistema.presentation.logIn.LogInModel;
import sistema.presentation.logIn.loginForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {

    private LogInModel model;
    private LogInController controller;
    private loginForm view;

    public Application() {
        model = new LogInModel();
        view = new loginForm(null); // JDialog modal
        controller = new LogInController(model, view);
        view.setController(controller);
        view.setModel(model);

        Service service = Service.instance();
    }

    private boolean doLogin() {
        view.setVisible(true);
        return model.getCurrent() != null;
    }

    private void doRun() {
        Usuario current = model.getCurrent();

        if (current == null) {
            JOptionPane.showMessageDialog(null, "No se pudo iniciar sesión");
            return;
        }

        Departamento dep = current.getDepartamento();
        if (dep == null) {
            JOptionPane.showMessageDialog(null, "El usuario no tiene departamento asignado");
            return;
        }

        JFrame mainMenu = null;

        switch (dep.getCodigo()) {
            case "001": // Administrador
                mainMenu = new MenuAdmin();
                break;
            case "002": // Médico
                mainMenu = new MenuMedico();
                break;
            case "003": // Farmacéutico
                mainMenu = new MenuFarmaceutico();
                break;
            default:
                JOptionPane.showMessageDialog(null,
                        "Departamento no reconocido: " + dep.getNombre());
                return;
        }

        // Configurar el cierre de la aplicación para guardar datos automáticamente
        if (mainMenu != null) {
            final JFrame menu = mainMenu;
            menu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Service.instance().guardarManualmente();
                    System.exit(0);
                }
            });

            SwingUtilities.invokeLater(() -> menu.setVisible(true));
        }
    }

    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            if (app.doLogin()) {
                app.doRun();
            } else {
                System.exit(0);
            }
        });
    }
}