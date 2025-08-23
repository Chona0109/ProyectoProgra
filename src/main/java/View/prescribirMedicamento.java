package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class prescribirMedicamento extends JDialog{
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTable miTabla;
    private JButton okButton;
    private JButton cancelarButton;
    private JPanel main;
    private JScrollPane scrollPane1;


    public prescribirMedicamento(JFrame parent) {
        super(parent);
        setTitle("Prescribe Medicamento");
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
         prescribirMedicamento prescribirMedicamento= new prescribirMedicamento(null);
    }

    private void createUIComponents() {
        miTabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Presentación");
        miTabla.setModel(modelo);
    }
}


