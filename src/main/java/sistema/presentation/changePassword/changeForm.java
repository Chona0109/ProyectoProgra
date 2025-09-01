package sistema.presentation.changePassword;

import javax.swing.*;

public class changeForm extends JDialog {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JButton button1;
    private JButton button2;
    private JPanel principal;

    public changeForm(JFrame parent) {
        super(parent);
        setTitle("Cambiar Contraseña");
        setContentPane(principal);
        setSize(600,500);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation( DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public JPanel getPanel() {
        return principal;
    }

    public static void main(String[] args) {
        changeForm changeForm = new changeForm(null);
    }
}
