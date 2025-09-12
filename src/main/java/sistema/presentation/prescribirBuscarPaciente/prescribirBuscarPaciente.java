package sistema.presentation.prescribirBuscarPaciente;

import sistema.logic.Service;
import sistema.logic.entities.Paciente;
import sistema.presentation.tableModels.PacientesTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

public class prescribirBuscarPaciente extends JDialog implements PropertyChangeListener {

    private JPanel main;
    private JComboBox<String> buscarComboBox;
    private JTextField buscarFld;
    private JTable miTabla;
    private JButton okButton;
    private JButton cancelarButton;

    private prescribirBuscarPacienteController controller;
    private prescribirBuscarPacienteModel model;
    private PacientesTableModel tableModel;

    public prescribirBuscarPaciente(Window parent) {
        super(parent, "Buscar Paciente", ModalityType.APPLICATION_MODAL);
        setContentPane(main);
        setSize(650, 450);
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = miTabla.getSelectedRow();
                if (row >= 0 && controller != null) {
                    String id = (String) miTabla.getValueAt(row, 0);
                    controller.seleccionarPaciente(id);
                    dispose();
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        buscarFld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filtrar(); }

            private void filtrar() {
                if (controller != null) {
                    String criterio = (String) buscarComboBox.getSelectedItem();
                    String valor = buscarFld.getText().trim();
                    controller.search(criterio, valor);
                }
            }
        });

        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setController(prescribirBuscarPacienteController controller) {
        this.controller = controller;
    }

    public void setModel(prescribirBuscarPacienteModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        tableModel = new PacientesTableModel(
                new int[]{PacientesTableModel.ID, PacientesTableModel.NOMBRE,
                        PacientesTableModel.FECHA, PacientesTableModel.TELEFONO},
                model.getList() != null ? model.getList() : Collections.emptyList()
        );
        miTabla.setModel(tableModel);
    }



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case prescribirBuscarPacienteModel.LIST:
                int[] cols = {
                        PacientesTableModel.ID,
                        PacientesTableModel.NOMBRE,
                        PacientesTableModel.FECHA,
                        PacientesTableModel.TELEFONO
                };
                miTabla.setModel(new PacientesTableModel(cols, model.getList()));
                break;

            case prescribirBuscarPacienteModel.CURRENT:
                break;
        }

        main.revalidate();
    }


    private void createUIComponents() {
        miTabla = new JTable();
        miTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
