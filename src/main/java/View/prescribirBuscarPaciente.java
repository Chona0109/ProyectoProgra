package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class prescribirBuscarPaciente extends JDialog {
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTable miTabla;
    private JButton okButton;
    private JButton cancelarButton;
    private JPanel main;

    public prescribirBuscarPaciente(JFrame parent) {
        super(parent);
        setTitle("Prescribir Buscar Paciente");
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
        prescribirBuscarPaciente prescribirBuscarPaciente= new prescribirBuscarPaciente(null);
    }

    private void createUIComponents() {
        miTabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Telefono");
        modelo.addColumn("Fec. Nac.");
        miTabla.setModel(modelo);
    }
}
