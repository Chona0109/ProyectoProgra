package sistema.presentation.logIn;

import sistema.logic.entities.Usuario;
import sistema.presentation.MenuAdmin;
import sistema.presentation.MenuFarmaceutico;
import sistema.presentation.MenuMedico;

import javax.swing.*;

public class loginForm extends JDialog {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton okButton;
    private JButton limpiarButton;
    private JButton changePasswordButton;
    private JPanel principal;

    private LogInController controller;
    private LogInModel model;
    private boolean listenersInit = false; // evita múltiples listeners

    public loginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(principal);
        setSize(600, 500);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void setController(LogInController controller) {
        this.controller = controller;
        initListeners();
    }

    // Asignar modelo
    public void setModel(LogInModel model) {
        this.model = model;
    }

    private void initListeners() {
        if (listenersInit) return;
        listenersInit = true;

        // Login
        okButton.addActionListener(e -> {
            if (controller != null) {
                try {
                    String id = textField1.getText();
                    String clave = new String(passwordField1.getPassword());
                    Usuario u = new Usuario(id, "");

                    controller.login(u);
                    Usuario current = model.getCurrent();

                    JOptionPane.showMessageDialog(loginForm.this,
                            "¡Bienvenido " + current.getNombre() + "!");


                    if (current.getDepartamento() != null) {
                        switch (current.getDepartamento().getCodigo()) {
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
                                JOptionPane.showMessageDialog(loginForm.this,
                                        "Departamento no reconocido: " + current.getDepartamento().getNombre());
                        }
                    }

                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginForm.this,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField1.setText("");
                }
            }
        });

        // Limpiar campos
        limpiarButton.addActionListener(e -> {
            textField1.setText("");
            passwordField1.setText("");
            if (controller != null) controller.clear();
        });

        // Cerrar ventana
        changePasswordButton.addActionListener(e -> dispose());
    }

    public JPanel getPanel() {
        return principal;
    }

    // Main de prueba
    public static void main(String[] args) {
        LogInModel model = new LogInModel();
        loginForm view = new loginForm(null);
        LogInController controller = new LogInController(model, view);
        view.setController(controller);
        view.setModel(model);
        view.setVisible(true); // mostrar ventana **solo una vez**
    }
}
