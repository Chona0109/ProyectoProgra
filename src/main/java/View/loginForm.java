package View;

import javax.swing.*;

public class loginForm extends JDialog{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel principal;

    public loginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(principal);
        setSize(600,550);
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
        loginForm loginForm = new loginForm(null);
    }
}
