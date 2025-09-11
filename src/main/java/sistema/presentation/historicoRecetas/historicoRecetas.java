package sistema.presentation.historicoRecetas;

import sistema.logic.entities.MedicamentoDetalle;
import sistema.logic.entities.Receta;
import sistema.presentation.tableModels.HistoricoRecetasTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class historicoRecetas extends JDialog implements PropertyChangeListener {

    private JPanel main;
    private JTextField idFld;
    private JButton buscarButton;
    private JButton verTodasButton;
    private JTable miTabla;
    private JButton verDetallesButton;

    private historicoRecetasModel model;
    private historicoRecetasController controller;

    public historicoRecetas(JFrame parent, historicoRecetasModel model, historicoRecetasController controller) {
        super(parent, "Histórico de Recetas", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(1000, 600); // Aumentado para mostrar más columnas
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupEventListeners();

        model.addPropertyChangeListener(this);

        try {
            controller.refresh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(main, "Error al cargar recetas: " + e.getMessage());
        }
    }

    private void setupEventListeners() {
        // Búsqueda en tiempo real mientras escribes
        idFld.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String id = idFld.getText().trim();
                controller.buscarPorId(id);
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFld.getText().trim();
                controller.buscarPorId(id);
            }
        });

        verTodasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idFld.setText(""); // Limpiar campo de búsqueda
                controller.refresh();
            }
        });

        verDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        // Listener para selección de fila en la tabla
        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && model.getCurrentList() != null && row < model.getCurrentList().size()) {
                    Receta recetaSeleccionada = model.getCurrentList().get(row);
                    model.setCurrent(recetaSeleccionada);
                }
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case historicoRecetasModel.LIST:
                actualizarTabla();
                break;
            case historicoRecetasModel.CURRENT:
                // Puedes agregar lógica adicional aquí si necesitas
                break;
        }
        this.main.revalidate();
    }

    private void actualizarTabla() {
        int[] cols = {
                HistoricoRecetasTableModel.ID,
                HistoricoRecetasTableModel.ESTADO,
                HistoricoRecetasTableModel.FECHA,
                HistoricoRecetasTableModel.PACIENTE,
                HistoricoRecetasTableModel.ID_PACIENTE,
                HistoricoRecetasTableModel.MEDICO
        };

        List<Receta> recetas = new ArrayList<>();
        if (model.getCurrentList() != null) {
            recetas = model.getCurrentList();
        }

        HistoricoRecetasTableModel tableModel = new HistoricoRecetasTableModel(cols, recetas);
        miTabla.setModel(tableModel);
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void showRecetaDetails(Receta receta) {
        if (receta == null) return;

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("ID: " + getValue(receta.getId(), "Sin ID")));
        panel.add(new JLabel("Estado: " + getValue(receta.getEstado(), "Sin estado")));
        panel.add(new JLabel("Fecha Confección: " + getValue(receta.getFechaConfeccion(), "Sin fecha")));
        panel.add(new JLabel("Fecha Retiro: " + getValue(receta.getFechaRetiro(), "Sin fecha")));
        panel.add(new JLabel("Paciente: " + getValue(receta.getPaciente() != null ? receta.getPaciente().getNombre() : null, "Sin paciente")));
        panel.add(new JLabel("ID Paciente: " + getValue(receta.getPaciente() != null ? receta.getPaciente().getId() : null, "Sin ID")));
        panel.add(new JLabel("Médico: " + getValue(receta.getMedico() != null ? receta.getMedico().getNombre() : null, "Sin médico")));

        if (receta.getMedicamentos() != null && !receta.getMedicamentos().isEmpty()) {
            panel.add(new JLabel("Cantidad de Medicamentos: " + receta.getMedicamentos().size()));

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
                    medicamentoInfo += ", Indicaciones: " + getValue(detalle.getIndicaciones(), "Sin indicaciones");
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

    private String getValue(Object value, String defaultValue) {
        return value != null ? value.toString() : defaultValue;
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