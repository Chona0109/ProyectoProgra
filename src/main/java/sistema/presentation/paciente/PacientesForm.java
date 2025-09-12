package sistema.presentation.paciente;

import sistema.logic.entities.Paciente;
import sistema.presentation.paciente.PacienteController;
import sistema.presentation.paciente.PacienteModel;
import sistema.presentation.tableModels.PacientesTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;

public class PacientesForm implements PropertyChangeListener {

    private JPanel main;
    private JTextField idFld;
    private JTextField nombreFld;
    private JTextField fechaFld;
    private JTextField telefonoFld;
    private JTextField busquedaFld;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;

    private PacienteController controller;
    private PacienteModel model;
    private PacientesTableModel tableModel;

    public PacientesForm() {

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Paciente p = take();
                    try {
                        controller.create(p);
                        JOptionPane.showMessageDialog(main, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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


        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFld.getText().trim();
                if (id.isEmpty()) return;

                int confirm = JOptionPane.showConfirmDialog(main, "¿Eliminar este paciente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(id);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.read(busquedaFld.getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && controller != null) {
                int row = miTabla.getSelectedRow();
                if (row >= 0) {
                    String id = (String) miTabla.getValueAt(row, 0);
                    try {
                        controller.read(id);
                    } catch (Exception ex) {
                        // Silenciar para no molestar al usuario
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return main;
    }

    public void setController(PacienteController controller) {
        this.controller = controller;
    }

    public void setModel(PacienteModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        tableModel = new PacientesTableModel(
                new int[]{PacientesTableModel.ID, PacientesTableModel.NOMBRE, PacientesTableModel.FECHA, PacientesTableModel.TELEFONO},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PacienteModel.LIST:
                int[] cols = {PacientesTableModel.ID, PacientesTableModel.NOMBRE, PacientesTableModel.FECHA, PacientesTableModel.TELEFONO};
                miTabla.setModel(new PacientesTableModel(cols, model.getList()));
                break;

            case PacienteModel.CURRENT:
                llenarFormulario();
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Paciente p = model.getCurrent();
            idFld.setText(p.getId());
            nombreFld.setText(p.getNombre());
            fechaFld.setText(p.getFechaNacimiento() != null ? p.getFechaNacimiento().toString() : "");
            telefonoFld.setText(p.getTelefono());
        }
    }

    private Paciente take() {
        Paciente p = new Paciente();
        p.setId(idFld.getText().trim());
        p.setNombre(nombreFld.getText().trim());

        try {
            if (!fechaFld.getText().trim().isEmpty()) {
                p.setFechaNacimiento(LocalDate.parse(fechaFld.getText().trim())); // formato ISO (yyyy-MM-dd)
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(main, "Formato de fecha inválido. Usa yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }

        p.setTelefono(telefonoFld.getText().trim());
        return p;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (idFld.getText().trim().isEmpty()) {
            valid = false;
            idFld.setBackground(Color.PINK);
        } else idFld.setBackground(Color.WHITE);

        if (nombreFld.getText().trim().isEmpty()) {
            valid = false;
            nombreFld.setBackground(Color.PINK);
        } else nombreFld.setBackground(Color.WHITE);

        if (!fechaFld.getText().trim().isEmpty()) {
            try {
                LocalDate.parse(fechaFld.getText().trim()); // validar formato ISO
                fechaFld.setBackground(Color.WHITE);
            } catch (DateTimeParseException ex) {
                valid = false;
                fechaFld.setBackground(Color.PINK);
            }
        }

        return valid;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
