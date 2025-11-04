package sistema.presentation.changePassword;

import javax.swing.*;
import java.awt.*;

public class changeForm extends JDialog {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton button1;
    private JButton button2;
    private JPanel principal;
    private JLabel Idtext;
    private JTextField IDField;

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


        button1.addActionListener(e -> {
            if (validateForm()) {
                try {

                    String userId = IDField.getText().trim();
                    String oldPass = new String(passwordField1.getPassword());
                    String newPass = new String(passwordField2.getPassword());
                    String confirmPass = new String(passwordField3.getPassword());

                    controller.changePassword(userId, oldPass, newPass, confirmPass);

                    JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.");
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        button2.addActionListener(e -> dispose());
    }

    public JPanel getPanel() {
        return principal;
    }
    private boolean validateForm() {
        boolean valid = true;

        if (IDField.getText().trim().isEmpty()) {
            valid = false; IDField.setBackground(Color.PINK);
        } else IDField.setBackground(Color.WHITE);

        if (passwordField1.getText().trim().isEmpty()) {
            valid = false; passwordField1.setBackground(Color.PINK);
        } else passwordField1.setBackground(Color.WHITE);

        if (passwordField2.getText().trim().isEmpty()) {
            valid = false; passwordField2.setBackground(Color.PINK);
        } else passwordField2.setBackground(Color.WHITE);

        if (passwordField3.getText().trim().isEmpty()) {
            valid = false; passwordField3.setBackground(Color.PINK);
        } else passwordField3.setBackground(Color.WHITE);


        return valid;
    }

    public static void main(String[] args) {
        ChangePasswordModel model = new ChangePasswordModel();
        changeForm view = new changeForm(null);
        ChangePasswordController controller = new ChangePasswordController(model, view);
        view.setVisible(true);
    }
}