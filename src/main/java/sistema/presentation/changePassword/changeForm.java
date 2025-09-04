package sistema.presentation.changePassword;

import sistema.logic.entities.Usuario;

import javax.swing.*;

public class changeForm extends JDialog {
    private JPasswordField passwordField1; // contraseña actual
    private JPasswordField passwordField2; // nueva contraseña
    private JPasswordField passwordField3; // confirmar contraseña
    private JButton button1; // aceptar
    private JButton button2; // cancelar
    private JPanel principal;

    private ChangePasswordController controller;
    private ChangePasswordModel model;
    private boolean listenersInit = false;

    public changeForm(JFrame parent) {
        super(parent);
        setTitle("Cambiar Contraseña");
        setContentPane(principal);
        setSize(600, 500);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setController(ChangePasswordController controller) {
        this.controller = controller;
        initListeners();
    }

    public void setModel(ChangePasswordModel model) {
        this.model = model;
    }

    private void initListeners() {
        if (listenersInit) return;
        listenersInit = true;

        // Aceptar
        button1.addActionListener(e -> {
            try {
                String oldPass = new String(passwordField1.getPassword());
                String newPass = new String(passwordField2.getPassword());
                String confirmPass = new String(passwordField3.getPassword());

                controller.changePassword(oldPass, newPass, confirmPass);
                JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancelar
        button2.addActionListener(e -> dispose());
    }

    public JPanel getPanel() {
        return principal;
    }

    public static void main(String[] args) {
        ChangePasswordModel model = new ChangePasswordModel();
        changeForm view = new changeForm(null);
        ChangePasswordController controller = new ChangePasswordController(model, view);
        view.setVisible(true);
    }
}