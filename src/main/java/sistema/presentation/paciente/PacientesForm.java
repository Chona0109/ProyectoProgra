package sistema.presentation.paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PacientesForm {
    private JPanel main;
    private JTextField idFld;
    private JTextField nombreFld;
    private JTextField busquedaFld;
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
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        miTabla.setModel(modelo);
    }
}
