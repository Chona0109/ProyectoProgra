package sistema.presentation.Despacho;

import sistema.logic.entities.Receta;
import sistema.presentation.tableModels.MedicamentosRecetaTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;

public class DespachoForm extends JDialog {
    private JPanel main;
    private JButton buscarRecetaButton;
    private JButton avanzarEstadoButton;
    private JButton limpiarButton;
    private JButton detallesButton;
    private JTable miTabla;
    private JLabel recetaLabel;

    private DespachoController controller;
    private DespachoModel model;

    private MedicamentosRecetaTableModel tableModel;

    public DespachoForm(JFrame parent, DespachoModel model, DespachoController controller) {
        super(parent, "Despacho de Recetas", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initListeners();
        updateTable(Collections.emptyList()); // inicializar la tabla vacía
    }

    private void initListeners() {
        // Buscar receta
        buscarRecetaButton.addActionListener((ActionEvent e) -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese el ID de la Receta:");
            if (id != null && !id.trim().isEmpty()) {
                try {
                    Receta r = controller.buscarReceta(id.trim());
                    recetaLabel.setText("Receta #" + r.getId() + " - Estado: " + r.getEstado());
                    updateTable(r.getMedicamentos());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Avanzar estado (Proceso → Lista → Entregada)
        avanzarEstadoButton.addActionListener((ActionEvent e) -> {
            try {
                controller.avanzarEstado(model.getCurrent());
                recetaLabel.setText("Receta #" + model.getCurrent().getId() +
                        " - Estado: " + model.getCurrent().getEstado());
                JOptionPane.showMessageDialog(this, "Estado actualizado con éxito");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Detalles de medicamento
        detallesButton.addActionListener((ActionEvent e) -> {
            int row = miTabla.getSelectedRow();
            if (row >= 0) {
                JOptionPane.showMessageDialog(this,
                        "Detalles del medicamento: " + tableModel.getValueAt(row, 0),
                        "Detalles", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un medicamento", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Limpiar pantalla
        limpiarButton.addActionListener((ActionEvent e) -> {
            model.setCurrent(new Receta());
            recetaLabel.setText("Receta");
            updateTable(Collections.emptyList());
        });
    }

    private void updateTable(java.util.List<sistema.logic.entities.MedicamentoDetalle> detalles) {
        if (tableModel == null) {
            int[] cols = {
                    MedicamentosRecetaTableModel.MEDICAMENTO,
                    MedicamentosRecetaTableModel.PRESENTACION,
                    MedicamentosRecetaTableModel.CANTIDAD,
                    MedicamentosRecetaTableModel.INDICACIONES,
                    MedicamentosRecetaTableModel.DIAS
            };
            tableModel = new MedicamentosRecetaTableModel(cols, detalles);
            miTabla.setModel(tableModel);
        } else {
            tableModel.setRows(detalles);
        }
    }
    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recetaLabel = new JLabel("Receta");
    }

    public JPanel getPanel() {
        return main;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DespachoModel model = new DespachoModel();
            DespachoController controller = new DespachoController(model);

            DespachoForm dialog = new DespachoForm(null, model, controller);
            dialog.setVisible(true);
        });
    }
}