package sistema.presentation.logIn;

import sistema.logic.entities.Usuario;
import sistema.presentation.MenuAdmin;
import sistema.presentation.MenuFarmaceutico;
import sistema.presentation.MenuMedico;
import sistema.presentation.changePassword.ChangePasswordController;
import sistema.presentation.changePassword.ChangePasswordModel;
import sistema.presentation.changePassword.changeForm;

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
                    u.setClave(clave);

                    controller.login(u);
                    Usuario current = model.getCurrent();

                    JOptionPane.showMessageDialog(loginForm.this,
                            "¡Bienvenido " + current.getNombre() + "!");


                    if (current.getDepartamento() != null) {
                        switch (current.getDepartamento().getCodigo()) {
                            case "001":
                                SwingUtilities.invokeLater(() -> new MenuAdmin().setVisible(true));
                                break;
                            case "002":
                                SwingUtilities.invokeLater(() -> new MenuMedico().setVisible(true));
                                break;
                            case "003":
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


        limpiarButton.addActionListener(e -> {
            textField1.setText("");
            passwordField1.setText("");
            if (controller != null) controller.clear();
        });

        changePasswordButton.addActionListener(e -> {

            ChangePasswordModel cpModel = new ChangePasswordModel();
            changeForm cpView = new changeForm(null);
            ChangePasswordController cpController = new ChangePasswordController(cpModel, cpView);
            cpView.setController(cpController);
            cpView.setModel(cpModel);
            cpView.setVisible(true);
        });

    }

    public JPanel getPanel() {
        return principal;
    }

}
