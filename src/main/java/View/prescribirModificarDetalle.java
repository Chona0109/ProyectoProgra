package View;

import javax.swing.*;

public class prescribirModificarDetalle extends JDialog {
    private JButton guardarButton;
    private JButton cancelarButton;
    private JTextField textField1;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JPanel main;

    public prescribirModificarDetalle(JFrame parent) {
        super(parent);
        setTitle("Prescribir Modificar Detalle");
        setContentPane(main);
        setSize(550,310);
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
        prescribirModificarDetalle prescribirModificarDetalle= new prescribirModificarDetalle(null);
    }
}
