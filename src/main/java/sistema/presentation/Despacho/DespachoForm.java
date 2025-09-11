package sistema.presentation.Despacho;

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

public class DespachoForm extends JDialog implements PropertyChangeListener {
    private JPanel main;
    private JButton avanzarEstadoButton;
    private JButton detallesButton;
    private JTable miTabla;
    private JLabel recetaLabel;
    private JTextField idFld;

    private DespachoController controller;
    private DespachoModel model;

    public DespachoForm(JFrame parent, DespachoModel model, DespachoController controller) {
        super(parent, "Despacho de Recetas", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupEventListeners();

        // Configurar el modelo para recibir notificaciones
        model.addPropertyChangeListener(this);
    }

    private void setupEventListeners() {
        idFld.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String idPaciente = idFld.getText().trim();
                controller.buscarPorIdPaciente(idPaciente);
            }
        });

        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && model.getList() != null && row < model.getList().size()) {
                    Receta recetaSeleccionada = model.getList().get(row);
                    model.setCurrent(recetaSeleccionada);
                }
            }
        });

        avanzarEstadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Receta receta = take();
                    try {
                        controller.avanzarEstado(receta);
                        JOptionPane.showMessageDialog(DespachoForm.this, "Estado actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DespachoForm.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        detallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && model.getList() != null && row < model.getList().size()) {
                    Receta receta = model.getList().get(row);

                    String detalles = "Receta ID: " + receta.getId() + "\n" +
                            "Paciente: " + (receta.getPaciente() != null ? receta.getPaciente().getNombre() : "N/A") + "\n" +
                            "Médico: " + (receta.getMedico() != null ? receta.getMedico().getNombre() : "N/A") + "\n" +
                            "Estado: " + receta.getEstado() + "\n" +
                            "Medicamentos: " + (receta.getMedicamentos() != null ? receta.getMedicamentos().size() : 0);

                    JOptionPane.showMessageDialog(DespachoForm.this, detalles, "Detalles de Receta", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(DespachoForm.this, "Seleccione una receta del historial", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case DespachoModel.LIST:
                actualizarTabla();
                break;

            case DespachoModel.CURRENT:
                llenarFormulario();
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Receta receta = model.getCurrent();

            if (receta.getId() != null && !receta.getId().isEmpty()) {
                recetaLabel.setText("Receta #" + receta.getId() + " - Estado: " +
                        (receta.getEstado() != null ? receta.getEstado() : "N/A"));
            } else {
                recetaLabel.setText("Receta");
            }
        }
    }

    private void actualizarTabla() {
        int[] cols = {
                HistoricoRecetasTableModel.ID,
                HistoricoRecetasTableModel.ESTADO,
                HistoricoRecetasTableModel.FECHA,
                HistoricoRecetasTableModel.PACIENTE
        };

        List<Receta> recetas = new ArrayList<>();
        if (model.getList() != null) {
            recetas = model.getList();
        }

        HistoricoRecetasTableModel tableModel = new HistoricoRecetasTableModel(cols, recetas);
        miTabla.setModel(tableModel);
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private Receta take() {
        return model.getCurrent();
    }

    private boolean validateForm() {
        boolean valid = true;
        Receta current = model.getCurrent();

        if (current == null || current.getId() == null || current.getId().isEmpty()) {
            valid = false;
            recetaLabel.setBackground(Color.PINK);
        } else {
            recetaLabel.setBackground(Color.WHITE);
        }

        return valid;
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