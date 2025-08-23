package View;

import javax.swing.*;

public class MedicosForm extends JDialog {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JPanel main;

    public MedicosForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(main);
        setSize(650,450);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation( DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public JPanel getPanel() {
        return main;
    }

    public static void main(String[] args) {
        MedicosForm medicosForm = new MedicosForm(null);
    }
}
