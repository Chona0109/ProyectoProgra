package View;

import logic.controllers.MedicosController;
import logic.controllers.DepartamentosController;
import logic.models.MedicosModel;
import logic.models.DepartamentosModel;
import logic.entidades.Medico;
import logic.entidades.Departamento;
import logic.TableModels.MedicosTableModel;

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
    private JTextField buscarIdFld;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;
    private JLabel departamento;
    private JButton buscarDepartamento;

    private Departamentos departamentosView;
    private DepartamentosModel departamentosModel;
    private DepartamentosController depController;

    private MedicosController controller;
    private MedicosModel model;
    private MedicosTableModel tableModel;

    public MedicosForm() {
        departamentosView = new Departamentos();
        departamentosModel = new DepartamentosModel();

        depController = new DepartamentosController(departamentosView, departamentosModel);

        // Acción para abrir selector de departamento
        buscarDepartamento.addActionListener(e -> {
            if (model.getCurrent() != null) {
                // Crear controlador de departamentos si no existe
                DepartamentosController depController = new DepartamentosController(departamentosView, departamentosModel);
                depController.setCurrentEntity(model.getCurrent()); // <-- asigna el medico actual
                departamentosView.setVisible(true);

                // Actualizar la etiqueta de departamento al cerrar el diálogo
                if (model.getCurrent().getDepartamento() != null) {
                    departamento.setText(model.getCurrent().getDepartamento().getNombre());
                }
            }
        });


        // Guardar médico
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (validateForm()) {
                    Medico medico = take();
                    try {
                        controller.create(medico);
                        JOptionPane.showMessageDialog(main, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Limpiar formulario
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        // Borrar médico
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFld.getText().trim();
                if (id.isEmpty()) return;

                int confirm = JOptionPane.showConfirmDialog(main, "¿Eliminar este médico?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try { controller.delete(id); } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Buscar médico por ID
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

        // Reporte general
        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try { controller.search(); } catch (Exception ex) {}
            }
        });

        // Selección en tabla
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && controller != null) {
                int row = miTabla.getSelectedRow();
                if (row >= 0) {
                    String id = (String) miTabla.getValueAt(row, 0);
                    try { controller.read(id); } catch (Exception ex) {}
                }
            }
        });
    }

    public JPanel getPanel() { return main; }

    public void setController(MedicosController controller) { this.controller = controller; }

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
                llenarFormulario();
                break;

            case MedicosModel.DEPARTMENTS:
                // si quieres actualizar algo con la lista de departamentos
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Medico m = model.getCurrent();
            idFld.setText(m.getId());
            nameFld.setText(m.getNombre());
            especialidadFld.setText(m.getEspecialidad());
            if (m.getDepartamento() != null) {
                departamento.setText(m.getDepartamento().getNombre());
            } else {
                departamento.setText("No seleccionado");
            }
        }
    }

    private Medico take() {
        Medico m = new Medico();
        m.setId(idFld.getText().trim());
        m.setNombre(nameFld.getText().trim());
        m.setEspecialidad(especialidadFld.getText().trim());
        m.setDepartamento(model.getCurrent().getDepartamento());
        return m;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (idFld.getText().trim().isEmpty()) { valid = false; idFld.setBackground(Color.PINK); }
        else idFld.setBackground(null);

        if (nameFld.getText().trim().isEmpty()) { valid = false; nameFld.setBackground(Color.PINK); }
        else nameFld.setBackground(null);

        if (especialidadFld.getText().trim().isEmpty()) { valid = false; especialidadFld.setBackground(Color.PINK); }
        else especialidadFld.setBackground(null);

        if (model.getCurrent().getDepartamento() == null) { valid = false; departamento.setBackground(Color.PINK); }
        else departamento.setBackground(null);

        return valid;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
