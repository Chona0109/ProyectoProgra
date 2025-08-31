package View;

import logic.controllers.DepartamentosController;
import logic.models.DepartamentosModel;
import logic.TableModels.DepartamentoTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Departamentos extends JDialog implements PropertyChangeListener {
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombre;
    private JButton buscar;
    private JTable list;
    private JPanel contentPane;

    private DepartamentosController controller;
    private DepartamentosModel model;

    public Departamentos() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        setTitle("Departamentos");
        setSize(400, 250);

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchDepartamentos(nombre.getText());
            }
        });

        buttonOK.addActionListener(e -> {
            if (list.getSelectedRow() >= 0 && controller != null) {
                controller.setDepartamento(list.getSelectedRow()); // asigna departamento al usuario
                Departamentos.this.setVisible(false);
            }
        });


        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamentos.this.setVisible(false);
            }
        });
    }

    public void setController(DepartamentosController controller) { this.controller = controller; }

    public void setModel(DepartamentosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(DepartamentosModel.DEPARTMENTS)) {
            int[] cols = {DepartamentoTableModel.CODIGO, DepartamentoTableModel.NOMBRE};
            list.setModel(new DepartamentoTableModel(cols, model.getDepartamentos()));
        }
        this.contentPane.revalidate();
    }
}
