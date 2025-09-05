package sistema.presentation.prescribirReceta;

import sistema.logic.entities.MedicamentoDetalle;
import sistema.logic.entities.Medicamento;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.Receta;
import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPaciente;
import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPacienteController;
import sistema.presentation.prescribirBuscarPaciente.prescribirBuscarPacienteModel;
import sistema.presentation.prescribirMedicamento.prescribirMedicamento;
import sistema.presentation.prescribirMedicamento.prescribirBuscarMedicamentoController;
import sistema.presentation.prescribirMedicamento.prescribirBuscarMedicamentoModel;
import sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle;
import sistema.presentation.tableModels.MedicamentosRecetaTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class prescribirReceta extends JDialog {

    private JPanel main;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JTable miTabla;
    private JLabel paciente;

    private Paciente pacienteSeleccionado;
    private List<MedicamentoDetalle> listaDetalles = new ArrayList<>();
    private MedicamentosRecetaTableModel tableModel;

    private prescribirRecetaModel model;
    private prescribirRecetaController controller;


    public prescribirReceta(JFrame parent, prescribirRecetaModel model, prescribirRecetaController controller) {
        super((JFrame) null, "Prescribir Receta", true);
        this.model = model;
        this.controller = controller;

        setContentPane(main);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setupEventListeners();
    }

    private void setupEventListeners() {
        buscarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prescribirBuscarPacienteModel pacienteModel = new prescribirBuscarPacienteModel();
                prescribirBuscarPacienteController pacienteController = new prescribirBuscarPacienteController(pacienteModel);

                prescribirBuscarPaciente dialog = new prescribirBuscarPaciente(prescribirReceta.this);
                dialog.setModel(pacienteModel);
                dialog.setController(pacienteController);
                dialog.setVisible(true);

                if (pacienteModel.getCurrent() != null) {
                    pacienteSeleccionado = pacienteModel.getCurrent();
                    paciente.setText(pacienteSeleccionado.getNombre());
                }
            }
        });

        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prescribirBuscarMedicamentoModel medicamentoModel = new prescribirBuscarMedicamentoModel();
                prescribirBuscarMedicamentoController medicamentoController = new prescribirBuscarMedicamentoController(medicamentoModel);

                prescribirMedicamento dialog = new prescribirMedicamento(prescribirReceta.this);
                dialog.setModel(medicamentoModel);
                dialog.setController(medicamentoController);
                dialog.setVisible(true);
            }
        });

        descartarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && row < listaDetalles.size()) {
                    listaDetalles.remove(row);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(main, "Seleccione un medicamento para descartar", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        detallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && row < listaDetalles.size()) {
                    MedicamentoDetalle detalle = listaDetalles.get(row);

                    prescribirModificarDetalle detalleDialog = new prescribirModificarDetalle(prescribirReceta.this, detalle.getMedicamento());

                    detalleDialog.setCantidad(detalle.getCantidad());
                    detalleDialog.setDias(detalle.getDias());
                    detalleDialog.setIndicaciones(detalle.getIndicaciones());

                    detalleDialog.setVisible(true);

                    if (detalleDialog.isGuardado()) {
                        detalle.setCantidad(detalleDialog.getCantidad());
                        detalle.setDias(detalleDialog.getDias());
                        detalle.setIndicaciones(detalleDialog.getIndicaciones());
                        actualizarTabla();
                    }
                } else {
                    JOptionPane.showMessageDialog(main, "Seleccione un medicamento para ver/modificar detalles", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        guardarButton.addActionListener(e -> {
            if (pacienteSeleccionado != null && !listaDetalles.isEmpty()) {
                try {
                    // Crear la receta
                    Receta receta = new Receta();
                    receta.setPaciente(pacienteSeleccionado);
                    receta.setMedicamentos(new ArrayList<>(listaDetalles));

                    // Llamar al controller para guardarla
                    controller.create(receta);

                    JOptionPane.showMessageDialog(main,
                            "Receta guardada para: " + pacienteSeleccionado.getNombre() +
                                    "\nCantidad de medicamentos: " + listaDetalles.size(),
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar UI
                    pacienteSeleccionado = null;
                    paciente.setText("Paciente");
                    listaDetalles.clear();
                    actualizarTabla();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main,
                            "Error al guardar la receta: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String mensaje = "Complete los siguientes campos:\n";
                if (pacienteSeleccionado == null) mensaje += "- Seleccione un paciente\n";
                if (listaDetalles.isEmpty()) mensaje += "- Agregue al menos un medicamento\n";
                JOptionPane.showMessageDialog(main, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });


        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pacienteSeleccionado = null;
                paciente.setText("Paciente");
                listaDetalles.clear();
                actualizarTabla();
            }
        });
    }

    public void agregarDetalle(MedicamentoDetalle detalle) {
        if (detalle != null && detalle.getMedicamento() != null) {
            listaDetalles.add(detalle);
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        int[] cols = {
                MedicamentosRecetaTableModel.MEDICAMENTO,
                MedicamentosRecetaTableModel.PRESENTACION,
                MedicamentosRecetaTableModel.CANTIDAD,
                MedicamentosRecetaTableModel.INDICACIONES,
                MedicamentosRecetaTableModel.DIAS
        };

        tableModel = new MedicamentosRecetaTableModel(cols, listaDetalles);
        miTabla.setModel(tableModel);
        main.revalidate();
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel = new MedicamentosRecetaTableModel(
                new int[]{
                        MedicamentosRecetaTableModel.MEDICAMENTO,
                        MedicamentosRecetaTableModel.PRESENTACION,
                        MedicamentosRecetaTableModel.CANTIDAD,
                        MedicamentosRecetaTableModel.INDICACIONES,
                        MedicamentosRecetaTableModel.DIAS
                },
                Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            prescribirRecetaModel model = new prescribirRecetaModel();
            prescribirRecetaController controller = new prescribirRecetaController(model);

            prescribirReceta dialog = new prescribirReceta(null, model, controller);
            dialog.setVisible(true);
        });
    }
}