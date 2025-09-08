package sistema.presentation.historicoRecetas;

import sistema.logic.Service;
import sistema.logic.entities.MedicamentoDetalle;
import sistema.logic.entities.Receta;
import sistema.presentation.tableModels.HistoricoRecetasTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class historicoRecetas extends JDialog {

    private JPanel main;
    private JTextField idFld;
    private JButton buscarButton;
    private JButton verTodasButton;
    private JTable miTabla;
    private JButton verDetallesButton;

    private HistoricoRecetasTableModel tableModel;
    private historicoRecetasModel model;
    private historicoRecetasController controller;

    public historicoRecetas(JFrame parent, historicoRecetasModel model, historicoRecetasController controller) {
        super(parent, "Histórico de Recetas", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initTable();
        setupEventListeners();

        try {
            controller.refresh();
            refreshTable(model.getList());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(main, "Error al cargar recetas: " + e.getMessage());
        }
    }

    private void initTable() {
        int[] cols = {
                HistoricoRecetasTableModel.ID,
                HistoricoRecetasTableModel.ESTADO,
                HistoricoRecetasTableModel.FECHA,
                HistoricoRecetasTableModel.PACIENTE
        };
        tableModel = new HistoricoRecetasTableModel(cols, Collections.emptyList());
        miTabla.setModel(tableModel);
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupEventListeners() {
        buscarButton.addActionListener(e -> {
            String id = idFld.getText().trim();
            if (!id.isEmpty()) {
                try {
                    controller.searchById(id);
                    refreshTable(model.getCurrentList());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main,
                            "Receta no encontrada: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        verTodasButton.addActionListener(e -> {
            try {
                controller.refresh();
                refreshTable(model.getList());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(main,
                        "Error al cargar recetas: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        verDetallesButton.addActionListener(e -> {
            int row = miTabla.getSelectedRow();
            List<Receta> recetas = model.getCurrentList();
            if (row >= 0 && row < recetas.size()) {
                Receta receta = recetas.get(row);
                showRecetaDetails(receta);
            } else {
                JOptionPane.showMessageDialog(main,
                        "Seleccione una receta para ver detalles",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void refreshTable(List<Receta> recetas) {
        tableModel.setRows(recetas);
        miTabla.revalidate();
        miTabla.repaint();
    }

    private void showRecetaDetails(Receta receta) {
        if (receta == null) return;

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("ID: " + (receta.getId() != null ? receta.getId() : "Sin ID")));
        panel.add(new JLabel("Estado: " + (receta.getEstado() != null ? receta.getEstado() : "Sin estado")));
        panel.add(new JLabel("Fecha Confección: " + (receta.getFechaConfeccion() != null ? receta.getFechaConfeccion() : "Sin fecha")));
        panel.add(new JLabel("Fecha Retiro: " + (receta.getFechaRetiro() != null ? receta.getFechaRetiro() : "Sin fecha")));
        panel.add(new JLabel("Paciente: " + (receta.getPaciente() != null ? receta.getPaciente().getNombre() : "Sin paciente")));
        panel.add(new JLabel("Médico: " + (receta.getMedico() != null ? receta.getMedico().getNombre() : "Sin médico")));

        if (receta.getMedicamentos() != null && !receta.getMedicamentos().isEmpty()) {
            panel.add(new JLabel("Cantidad de Medicamentos: " + receta.getMedicamentos().size()));

            // Mostrar medicamentos con validación de null
            for (int i = 0; i < receta.getMedicamentos().size(); i++) {
                MedicamentoDetalle detalle = receta.getMedicamentos().get(i);
                if (detalle != null) {
                    String medicamentoInfo = " - ";

                    if (detalle.getMedicamento() != null) {
                        medicamentoInfo += detalle.getMedicamento().getNombre();
                    } else {
                        medicamentoInfo += "Medicamento no disponible";
                    }

                    medicamentoInfo += ", Cantidad: " + detalle.getCantidad();
                    medicamentoInfo += ", Indicaciones: " + (detalle.getIndicaciones() != null ? detalle.getIndicaciones() : "Sin indicaciones");
                    medicamentoInfo += ", Días: " + detalle.getDias();

                    panel.add(new JLabel(medicamentoInfo));
                } else {
                    panel.add(new JLabel(" - Detalle de medicamento no disponible"));
                }
            }
        } else {
            panel.add(new JLabel("Cantidad de Medicamentos: 0"));
            panel.add(new JLabel("No hay medicamentos registrados"));
        }

        JOptionPane.showMessageDialog(this, panel, "Detalles de Receta", JOptionPane.INFORMATION_MESSAGE);
    }


    private void createUIComponents() {
        miTabla = new JTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            historicoRecetasModel model = new historicoRecetasModel();
            historicoRecetasController controller = new historicoRecetasController(model);
            historicoRecetas dialog = new historicoRecetas(null, model, controller);
            dialog.setVisible(true);
        });
    }

    public Component getPanel() {
        return main;
    }
}
