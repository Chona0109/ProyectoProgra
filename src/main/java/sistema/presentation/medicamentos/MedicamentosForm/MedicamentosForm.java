package sistema.presentation.medicamentos.MedicamentosForm;

import sistema.logic.entities.Medicamento;
import sistema.presentation.medicamentos.MedicamentosController;
import sistema.presentation.medicamentos.MedicamentosModel;
import sistema.presentation.tableModels.MedicamentosTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

public class MedicamentosForm implements PropertyChangeListener {
    private JPanel main;
    private JTextField CodigoButton;
    private JTextField DescripcionButton;
    private JTextField CodigoBuscar;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable miTabla;
    private JButton borrarButton;




    private MedicamentosController controller;
    private MedicamentosModel model;
    private MedicamentosTableModel tableModel;

    public JPanel getPanel() {
        return main;
    }

    private void createUIComponents() {
        miTabla = new JTable();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Descripcion");
        miTabla.setModel(modelo);

    }
    public MedicamentosForm() {

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    Medicamento m = take();
                    try {
                        controller.create(m);
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


        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = CodigoButton.getText().trim();
                if (codigo.isEmpty()) return;

                int confirm = JOptionPane.showConfirmDialog(main, "¿Eliminar este Medicamento?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.delete(codigo);
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
                    String codigo = CodigoBuscar.getText().trim();
                    controller.read(codigo);
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
                    try { controller.read(id); } catch (Exception ex) {}
                }
            }
        });
    }

    public void setController(MedicamentosController controller) {
        this.controller = controller;
    }

    public void setModel(MedicamentosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);



        tableModel = new MedicamentosTableModel(
                new int[]{MedicamentosTableModel.CODIGO, MedicamentosTableModel.DESCRIPCION},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicamentosModel.LIST:
                int[] cols = {MedicamentosTableModel.CODIGO, MedicamentosTableModel.DESCRIPCION};
                miTabla.setModel(new MedicamentosTableModel(cols, model.getList()));
                break;

            case MedicamentosModel.CURRENT:
                llenarFormulario();
                break;

        }
        main.revalidate();
    }

    private void llenarFormulario() {
        if (model.getCurrent() != null) {
            Medicamento m = model.getCurrent();
            CodigoButton.setText(m.getCodigo());
            DescripcionButton.setText(m.getDescripcion());

        }
    }


    private Medicamento take() {
        Medicamento m = new Medicamento();
        m.setCodigo(CodigoButton.getText().trim());
        m.setDescripcion(DescripcionButton.getText().trim());


        return m;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (CodigoButton.getText().trim().isEmpty()) {
            valid = false; CodigoButton.setBackground(Color.PINK);
        } else CodigoButton.setBackground(Color.WHITE);

        if (DescripcionButton.getText().trim().isEmpty()) {
            valid = false; DescripcionButton.setBackground(Color.PINK);
        } else DescripcionButton.setBackground(Color.WHITE);

        return valid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MedicamentosForm medicamentosForm = new MedicamentosForm();
            MedicamentosModel model = new MedicamentosModel();
            MedicamentosController controller = new MedicamentosController(medicamentosForm, model);

            medicamentosForm.setController(controller);
            medicamentosForm.setModel(model);

            JFrame frame = new JFrame("Formulario de Medicamentos");
            frame.setContentPane(medicamentosForm.getPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}

