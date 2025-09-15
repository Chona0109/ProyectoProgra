package sistema.presentation.logIn;

import sistema.logic.entities.Usuario;
import sistema.presentation.changePassword.ChangePasswordController;
import sistema.presentation.changePassword.ChangePasswordModel;
import sistema.presentation.changePassword.changeForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginForm extends JDialog {

    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton okButton;
    private JButton limpiarButton;
    private JButton changePasswordButton;
    private JPanel principal;

    private LogInController controller;
    private LogInModel model;
    private boolean listenersInit = false;

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

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    try {
                        String id = textField1.getText();
                        String clave = new String(passwordField1.getPassword());
                        Usuario u = new Usuario(id, "");
                        u.setClave(clave);

                        controller.login(u);
                        Usuario current = model.getCurrent();

                        JOptionPane.showMessageDialog(loginForm.this,
                                "¡Bienvenido!");

                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginForm.this,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        passwordField1.setText("");
                    }
                }
            }
        });


        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                passwordField1.setText("");
                if (controller != null) controller.clear();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePasswordModel cpModel = new ChangePasswordModel();
                changeForm cpView = new changeForm(null);
                ChangePasswordController cpController = new ChangePasswordController(cpModel, cpView);
                cpView.setController(cpController);
                cpView.setModel(cpModel);
                cpView.setVisible(true);
            }
        });
    }

    public JPanel getPanel() {
        return principal;
    }
}
