package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MedicamentosForm {
    private JPanel main;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;
    private JButton borrarButton;

    public JPanel getPanel() {
        return main;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        miTabla.setModel(modelo);
    }
}
