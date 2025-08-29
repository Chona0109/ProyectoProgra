package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FarmaceutasForm {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;
    private JPanel main;

    public JPanel getPanel() {
        return main;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        miTabla.setModel(modelo);
    }
}
