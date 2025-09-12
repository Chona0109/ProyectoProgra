package sistema.presentation.prescribirReceta;

import sistema.logic.entities.MedicamentoDetalle;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class prescribirReceta extends JDialog implements PropertyChangeListener {

    private JPanel main;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private JButton detallesButton;
    private JTable miTabla;
    private JLabel paciente;

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
                    Receta currentReceta = model.getCurrent();
                    currentReceta.setPaciente(pacienteModel.getCurrent());
                    model.setCurrent(currentReceta);
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
                Receta currentReceta = model.getCurrent();

                if (row >= 0 && currentReceta != null) {
                    try {
                        controller.removeMedicamento(currentReceta.getId(), row);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main,
                                "Error al descartar el medicamento: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(main,
                            "Seleccione un medicamento para descartar",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        detallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                controller.modificarDetalleMedicamento(row);
            }
        });



        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Receta receta = take();
                    try {
                        if (receta.getId() == null || receta.getId().isEmpty()) {
                            controller.create(receta);
                        } else {
                            controller.update(receta);
                            controller.clear();
                        }

                        JOptionPane.showMessageDialog(main,
                                "Receta guardada para: " + receta.getPaciente().getNombre() +
                                        "\nCantidad de medicamentos: " + receta.getMedicamentos().size(),
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main,
                                "Error al guardar la receta: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case prescribirRecetaModel.LIST:
                break;

            case prescribirRecetaModel.CURRENT:
                llenarFormulario();
                actualizarTabla();
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Receta receta = model.getCurrent();

            if (receta.getPaciente() != null) {
                paciente.setText(receta.getPaciente().getNombre());
            } else {
                paciente.setText("");
            }
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

        List<MedicamentoDetalle> medicamentos = new ArrayList<>();
        if (model.getCurrent() != null && model.getCurrent().getMedicamentos() != null) {
            medicamentos = model.getCurrent().getMedicamentos();
        }

        MedicamentosRecetaTableModel tableModel = new MedicamentosRecetaTableModel(cols, medicamentos);
        miTabla.setModel(tableModel);
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private Receta take() {
        return model.getCurrent();
    }

    private boolean validateForm() {
        boolean valid = true;
        Receta current = model.getCurrent();

        if (current == null || current.getPaciente() == null) {
            valid = false;
            paciente.setBackground(Color.PINK);
        } else {
            paciente.setBackground(Color.WHITE);
        }

        if (current == null || current.getMedicamentos() == null || current.getMedicamentos().isEmpty()) {
            valid = false;
            miTabla.setBackground(Color.PINK);
        } else {
            miTabla.setBackground(Color.WHITE);
        }

        return valid;
    }

    public void agregarDetalle(MedicamentoDetalle detalle) {
        if (detalle != null && detalle.getMedicamento() != null) {
            Receta currentReceta = model.getCurrent();
            if (currentReceta.getMedicamentos() == null) {
                currentReceta.setMedicamentos(new ArrayList<>());
            }
            currentReceta.getMedicamentos().add(detalle);
            model.setCurrent(currentReceta);
        }
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public Component getPanel() {
        return main;
    }
}
