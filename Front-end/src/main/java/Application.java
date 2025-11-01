

import logic.entities.Usuario;
import logic.entities.Departamento;
import sistema.logic.Proxy;
import sistema.presentation.MenuAdmin;
import sistema.presentation.MenuMedico;
import sistema.presentation.MenuFarmaceutico;
import sistema.presentation.logIn.LogInController;
import sistema.presentation.logIn.LogInModel;
import sistema.presentation.logIn.loginForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Wilson José Umaña Ríos - 1-1972-0629
//Andrey Jesús Solano Rojas 402690459
//Gabriel Sanchez Chacon 1197440164


public class Application {

    private LogInModel model;
    private LogInController controller;
    private loginForm view;

    public Application() {
        model = new LogInModel();
        view = new loginForm(null);
        controller = new LogInController(model, view);
        view.setController(controller);
        view.setModel(model);

        Proxy service = Proxy.instance();
    }

    private boolean doLogin() {
        view.setVisible(true);
        return model.getCurrent() != null;
    }

    private void doRun() {

        try {
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
                case "001":
                    System.out.println("entra");
                    mainMenu = new MenuAdmin();
                    System.out.println("Funciono");

                    break;
                case "002":
                    mainMenu = new MenuMedico();
                    break;
                case "003":
                    mainMenu = new MenuFarmaceutico();
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "Departamento no reconocido: " + dep.getNombre());
                    return;
            }


            if (mainMenu != null) {
                final JFrame menu = mainMenu;
                menu.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {

                        Proxy.instance().stop();
                        System.exit(0);
                    }
                });

                SwingUtilities.invokeLater(() -> {
                    menu.setVisible(true);
                    System.out.println("Mostrando menú principal...");
                });
            } else {
                JOptionPane.showMessageDialog(null, "Error: menú no creado.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en doRun: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al iniciar" + e.getMessage());
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