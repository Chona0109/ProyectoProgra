package sistema.presentation.Departamentos;

import sistema.presentation.medicos.MedicosController;
import sistema.presentation.medicos.MedicosModel;
import sistema.presentation.tableModels.DepartamentoTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Departamentos extends JDialog implements PropertyChangeListener {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombre;
    private JButton buscar;
    private JTable list;

    private MedicosController controller;
    private MedicosModel model;

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
                if (controller != null) {
                    controller.searchDepartamentos(nombre.getText());
                }
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.getSelectedRow() >= 0 && controller != null) {
                    controller.setDepartamento(list.getSelectedRow());
                    Departamentos.this.setVisible(false);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Departamentos.this.setVisible(false);
            }
        });
    }

    public void setController(MedicosController controller) {
        this.controller = controller;
    }

    public void setModel(MedicosModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicosModel.DEPARTMENTS:
                int[] cols = {DepartamentoTableModel.CODIGO, DepartamentoTableModel.NOMBRE};
                list.setModel(new DepartamentoTableModel(cols, model.getDepartamentos()));
                break;
        }
        contentPane.revalidate();
    }
}
