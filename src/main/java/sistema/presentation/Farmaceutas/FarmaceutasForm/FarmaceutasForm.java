package sistema.presentation.Farmaceutas.FarmaceutasForm;

import sistema.logic.entities.Farmaceutico;
import sistema.presentation.Farmaceutas.FarmaceutasController;
import sistema.presentation.Farmaceutas.FarmaceutasModel;
import sistema.presentation.tableModels.FarmaceuticosTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

public class FarmaceutasForm implements PropertyChangeListener {

    private JPanel main;
    private JTextField idFld;
    private JTextField nombreFld;
    private JTextField busquedaFld;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;

    private FarmaceutasController controller;
    private FarmaceutasModel model;
    private FarmaceuticosTableModel tableModel;

    public FarmaceutasForm() {
        // Guardar
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Farmaceutico f = take();
                    try {
                        controller.create(f);
                        JOptionPane.showMessageDialog(main, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Limpiar
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        // Borrar
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idFld.getText().trim();
                if (id.isEmpty()) return;

                int confirm = JOptionPane.showConfirmDialog(main, "¿Eliminar este farmaceuta?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(id);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(main, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Buscar
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

        // Selección en tabla
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        miTabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && controller != null) {
                int row = miTabla.getSelectedRow();
                if (row >= 0) {
                    String id = (String) miTabla.getValueAt(row, 0);
                    try {
                        controller.read(id);
                    } catch (Exception ex) {
                        // Silenciar para evitar molestias al usuario
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return main;
    }

    public void setController(FarmaceutasController controller) {
        this.controller = controller;
    }

    public void setModel(FarmaceutasModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        tableModel = new FarmaceuticosTableModel(
                new int[]{FarmaceuticosTableModel.ID, FarmaceuticosTableModel.NOMBRE},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case FarmaceutasModel.LIST:
                int[] cols = {FarmaceuticosTableModel.ID, FarmaceuticosTableModel.NOMBRE};
                miTabla.setModel(new FarmaceuticosTableModel(cols, model.getList()));
                break;

            case FarmaceutasModel.CURRENT:
                llenarFormulario();
                break;
        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Farmaceutico f = model.getCurrent();
            idFld.setText(f.getId());
            nombreFld.setText(f.getNombre());
        }
    }

    private Farmaceutico take() {
        Farmaceutico f = new Farmaceutico();
        f.setId(idFld.getText().trim());
        f.setNombre(nombreFld.getText().trim());
        return f;
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

        return valid;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
