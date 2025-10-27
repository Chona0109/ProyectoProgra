package sistema.presentation.medicos;

import sistema.logic.entities.Medico;
import sistema.presentation.Departamentos.Departamentos;
import sistema.presentation.tableModels.MedicosTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

public class MedicosForm implements PropertyChangeListener {

    private JPanel main;
    private JTextField idFld;
    private JTextField nameFld;
    private JTextField especialidadFld;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JTable miTabla;
    private JTextField buscarIdFld;
    private JButton reporteButton;

    private Departamentos departamentosView;

    private MedicosController controller;
    private MedicosModel model;
    private MedicosTableModel tableModel;

    public MedicosForm() {


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Medico m = take();
                    try {
                        if (idFld.isEnabled()) {
                            controller.create(m);
                            JOptionPane.showMessageDialog(main, "Médico agregado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            controller.update(m);
                            JOptionPane.showMessageDialog(main, "Médico actualizado correctamente", "", JOptionPane.INFORMATION_MESSAGE);
                        }
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

                int confirm = JOptionPane.showConfirmDialog(main, "¿Eliminar este médico?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
                    controller.read(buscarIdFld.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(main, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        miTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && miTabla.getSelectedRow() != -1) {
                    int row = miTabla.getSelectedRow();
                    MedicosTableModel tm = (MedicosTableModel) miTabla.getModel();
                    Medico seleccionado = tm.getRowAt(row);

                    try {
                        controller.read(seleccionado.getId());
                        controller.setCurrent(model.getCurrent());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public JPanel getPanel() { return main; }

    public void setController(MedicosController controller) {
        this.controller = controller;
        if (departamentosView != null) {
            departamentosView.setController(controller);
        }
    }

    public void setModel(MedicosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);


        tableModel = new MedicosTableModel(
                new int[]{MedicosTableModel.ID, MedicosTableModel.NOMBRE, MedicosTableModel.ESPECIALIDAD},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicosModel.LIST:
                int[] cols = {MedicosTableModel.ID, MedicosTableModel.NOMBRE, MedicosTableModel.ESPECIALIDAD};
                miTabla.setModel(new MedicosTableModel(cols, model.getList()));
                break;

            case MedicosModel.CURRENT:
                Medico m = model.getCurrent();
                if (m.getId() == null || m.getId().isEmpty()) {
                    idFld.setText("");
                    idFld.setEnabled(true);
                    nameFld.setText("");
                    especialidadFld.setText("");
                } else {
                    llenarFormulario();
                }
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Medico m = model.getCurrent();
            idFld.setText(m.getId());
            idFld.setEnabled(false);
            nameFld.setText(m.getNombre());
            especialidadFld.setText(m.getEspecialidad());
        }
    }


    private Medico take() {
        Medico m = new Medico();
        m.setId(idFld.getText().trim());
        m.setNombre(nameFld.getText().trim());
        m.setEspecialidad(especialidadFld.getText().trim());

        return m;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (idFld.getText().trim().isEmpty()) {
            valid = false; idFld.setBackground(Color.PINK);
        } else idFld.setBackground(Color.WHITE);

        if (nameFld.getText().trim().isEmpty()) {
            valid = false; nameFld.setBackground(Color.PINK);
        } else nameFld.setBackground(Color.WHITE);

        if (especialidadFld.getText().trim().isEmpty()) {
            valid = false; especialidadFld.setBackground(Color.PINK);
        } else especialidadFld.setBackground(Color.WHITE);


        return valid;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
