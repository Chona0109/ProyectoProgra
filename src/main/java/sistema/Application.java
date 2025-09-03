package sistema;

import sistema.logic.entities.Usuario;
import sistema.logic.entities.Departamento;
import sistema.presentation.MenuAdmin;
import sistema.presentation.MenuMedico;
import sistema.presentation.MenuFarmaceutico;
import sistema.presentation.logIn.LogInController;
import sistema.presentation.logIn.LogInModel;
import sistema.presentation.logIn.loginForm;

import javax.swing.*;

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
    }

    private boolean doLogin() {
        view.setVisible(true); // bloquea hasta que cierre
        return model.getCurrent() != null; // usuario válido
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

        switch (dep.getCodigo()) {
            case "001": // Administrador
                SwingUtilities.invokeLater(() -> new MenuAdmin().setVisible(true));
                break;
            case "002": // Médico
                SwingUtilities.invokeLater(() -> new MenuMedico().setVisible(true));
                break;
            case "003": // Farmacéutico
                SwingUtilities.invokeLater(() -> new MenuFarmaceutico().setVisible(true));
                break;
            default:
                JOptionPane.showMessageDialog(null,
                        "Departamento no reconocido: " + dep.getNombre());
        }
    }

    public static void main(String[] args) {
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